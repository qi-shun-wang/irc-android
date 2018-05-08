package com.ising99.intelligentremotecontrol.core.UPnP;

import android.util.Log;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.model.action.ActionInvocation;
import org.fourthline.cling.model.message.UpnpResponse;
import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.meta.Service;
import org.fourthline.cling.model.types.UDAServiceId;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;
import org.fourthline.cling.support.avtransport.callback.Play;
import org.fourthline.cling.support.avtransport.callback.SetAVTransportURI;
import org.fourthline.cling.support.avtransport.callback.Stop;

import java.io.IOException;
import java.net.InetAddress;

/**
 * Created by shun on 2018/5/4.
 * .
 */

public class DLNAMediaManager implements DLNAMediaManagerProtocol ,RegistryListener {

    public DLNAMediaManager(){
        upnpService = new UpnpServiceImpl(new AndroidUpnpServiceConfiguration());
    }

    private WebServer server;
    private UpnpService upnpService ;

    @Override
    public boolean play(Device device, String path, String data) {

        try {
            Service localService = device.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                Log.e("set url", "set url" + path);
                upnpService.getControlPoint().execute(new SetAVTransportURI(localService,"http://"+server.getHostname()+ ":"+server.getListeningPort() + path) {
                    @Override
                    public void success(ActionInvocation invocation) {
                        Log.i("DLNAMediaManager", "SetAVTransportURI success.");
                        upnpService.getControlPoint().execute(new Play(localService) {
                            @Override
                            public void success(ActionInvocation invocation) {
                                Log.i("DLNAMediaManager", "Play success.");
                            }

                            @Override
                            public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                                Log.e("DLNAMediaManager", "Play failed");
                            }
                        });
                    }

                    @Override
                    public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                        Log.e("DLNAMediaManager", "SetAVTransportURI failed");
                    }
                });

            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean goOn(Device device, String pausePosition) {
        return false;
    }

    @Override
    public String getTransportState(Device device) {
        return null;
    }

    @Override
    public int getMinVolumeValue(Device device) {
        return 0;
    }

    @Override
    public int getMaxVolumeValue(Device device) {
        return 0;
    }

    @Override
    public boolean seek(Device device, String targetPosition) {
        return false;
    }

    @Override
    public String getPositionInfo(Device device) {
        return null;
    }

    @Override
    public String getMediaDuration(Device device) {
        return null;
    }

    @Override
    public boolean setMute(Device device, String targetValue) {
        return false;
    }

    @Override
    public String getMute(Device device) {
        return null;
    }

    @Override
    public boolean setVoice(Device device, int value) {
        return false;
    }

    @Override
    public int getVoice(Device device) {
        return 0;
    }

    @Override
    public boolean stop(Device device) {
        try {
            Service localService = device.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new Stop(localService) {
                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        Log.e("DLNAMediaManager", "Stop failed");
                    }
                });

            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean pause(Device device) {
        return false;
    }

    @Override
    public boolean getPauseState(Device device) {
        return false;
    }

    @Override
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {

    }

    @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {

    }

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {

    }

    @Override
    public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {

    }

    @Override
    public void remoteDeviceRemoved(Registry registry, RemoteDevice device) {

    }

    @Override
    public void localDeviceAdded(Registry registry, LocalDevice device) {

    }

    @Override
    public void localDeviceRemoved(Registry registry, LocalDevice device) {

    }

    @Override
    public void beforeShutdown(Registry registry) {

    }

    @Override
    public void afterShutdown() {

    }

    public void setupMediaServer(InetAddress address) {
        server = new WebServer(address.getHostAddress(),8080);
    }

    public void startServer() throws IOException {
        server.start();
    }

    public void stopServer() {
        server.stop();
    }

}