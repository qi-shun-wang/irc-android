package com.ising99.intelligentremotecontrol.core.DeviceDiscovery;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import java.io.InterruptedIOException;
import java.io.Serializable;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;


@Deprecated
public final class UDPListenerService extends Service implements Serializable{

    private MulticastSocket socket;
    private Thread UDPBroadcastThread;
    private DeviceDiscoveryDelegate delegate;
    private final static int PORT = 9999;
    private final static String ADDRESS = "239.0.0.0";
    private boolean isStart = false;
    private byte[] buf;

    private void startMulticastListener() {
        UDPBroadcastThread = new Thread(() -> {
            if(UDPBroadcastThread.isInterrupted()) {return;}
            try {
                InetAddress address = InetAddress.getByName(ADDRESS);
                socket = new MulticastSocket(PORT);
                socket.joinGroup(address);
                buf = new byte[10240];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                while (isStart) {
                    socket.receive(packet);
                    String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                    if (delegate != null) {
                        delegate.didRecieved(msg);
                    }
                    Log.v("====packet===>", msg);
                }
            }catch(InterruptedIOException e){
                e.printStackTrace();
            }catch (Exception e ){
                e.printStackTrace();
            }
        });
        UDPBroadcastThread.start();
    }


    private void stopMulticastListener() {
        isStart = false;
        socket.close();
        UDPBroadcastThread.interrupt();
        delegate = null;
        buf = null;
        UDPBroadcastThread = null;
        Runtime.getRuntime().gc();

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDestroy() {
        stopMulticastListener();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        isStart = true;
        delegate = (DeviceDiscoveryDelegate) intent.getExtras().getParcelable("Delegation");
        startMulticastListener();
        Log.i("UDP", "Multicast Service started");
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
