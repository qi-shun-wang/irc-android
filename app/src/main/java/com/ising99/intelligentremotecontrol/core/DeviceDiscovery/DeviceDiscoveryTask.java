package com.ising99.intelligentremotecontrol.core.DeviceDiscovery;

import android.os.AsyncTask;
import android.util.Log;

import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by shun on 2018/3/29.
 *
 */

public class DeviceDiscoveryTask extends AsyncTask<DeviceDiscoveryDelegate, Void, Void> {

    private DeviceDiscoveryDelegate delegate;
    private final static int PORT = 9999;
    private final static String ADDRESS = "239.0.0.0";
    private boolean isStart = false;

    private void startMulticastListener() {

        try {
            InetAddress address = InetAddress.getByName(ADDRESS);
            MulticastSocket socket = new MulticastSocket(PORT);
            socket.joinGroup(address);
            byte[] buf = new byte[10240];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            while (isStart) {
                if (isCancelled()){Log.d("DeviceDiscoveryTask", " ===>isCancelled");return;}
                socket.receive(packet);
                String msg = new String(packet.getData(), packet.getOffset(), packet.getLength());
                Log.d("====packet===>", msg);
                if (delegate != null) {
                    Log.d("Delegate.didRecieved", msg);
                    delegate.didRecieved(msg);
                }
            }
        }catch(InterruptedIOException e){
            e.printStackTrace();
        }catch (Exception e ){
            e.printStackTrace();
        }
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
        isStart = false;
    }

    @Override
    protected Void doInBackground(DeviceDiscoveryDelegate... deviceDiscoveryDelegates) {
        isStart = true;
        delegate = deviceDiscoveryDelegates[0];
        startMulticastListener();
        return null;
    }
}
