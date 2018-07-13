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
import org.fourthline.cling.support.avtransport.callback.GetPositionInfo;
import org.fourthline.cling.support.avtransport.callback.Pause;
import org.fourthline.cling.support.avtransport.callback.Play;
import org.fourthline.cling.support.avtransport.callback.Seek;
import org.fourthline.cling.support.avtransport.callback.SetAVTransportURI;
import org.fourthline.cling.support.avtransport.callback.Stop;
import org.fourthline.cling.support.model.PositionInfo;
import org.fourthline.cling.support.renderingcontrol.callback.GetVolume;
import org.fourthline.cling.support.renderingcontrol.callback.SetVolume;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Formatter;

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
    private Device currentDevice;

    @Override
    public void setupMediaServer(InetAddress address) {
        server = new WebServer(address.getHostAddress(),8080);
    }

    @Override
    public void startServer() throws IOException {
        server.start();
    }

    @Override
    public void stopServer() {
        server.stop();
    }

    @Override
    public void setCurrentDevice(Device currentDevice) {
        this.currentDevice = currentDevice;
    }

    public Device getCurrentDevice() {
        return currentDevice;
    }

    @Override
    public void setAVTransportURI(String path, DLNAMediaManagerCallback.Common callback) {
        try {
            Service localService = currentDevice.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                Log.e("set url", "set url" + path);
                upnpService.getControlPoint().execute(new SetAVTransportURI(localService,"http://"+server.getHostname()+ ":"+server.getListeningPort() + path) {
                    @Override
                    public void success(ActionInvocation invocation) {
                        callback.success(invocation);
                    }

                    @Override
                    public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                        callback.failure(arg0,arg1,arg2);
                    }
                });

            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void play(DLNAMediaManagerCallback.Common callback) {

        try {
            Service localService = currentDevice.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new Play(localService) {
                    @Override
                    public void success(ActionInvocation invocation) {
                        callback.success(invocation);
                    }

                    @Override
                    public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                        callback.failure(arg0,arg1,arg2);
                    }
                });
            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void pause(DLNAMediaManagerCallback.Common callback) {
        try {
            Service localService = currentDevice.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new Pause(localService) {
                    @Override
                    public void success(ActionInvocation invocation) {
                        callback.success(invocation);
                    }

                    @Override
                    public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                        callback.failure(arg0,arg1,arg2);
                    }
                });
            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void stop(DLNAMediaManagerCallback.OneWay callback) {
        try {
            Service localService = currentDevice.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new Stop(localService) {
                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        callback.failure(invocation, operation, defaultMsg);
                    }
                });

            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void seek(long timeInterval, DLNAMediaManagerCallback.Common callback) {
        try {
            Service localService = currentDevice.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new Seek(localService,transformedFrom(timeInterval)) {

                    @Override
                    public void success(ActionInvocation invocation) {
                        callback.success(invocation);
                    }

                    @Override
                    public void failure(ActionInvocation arg0, UpnpResponse arg1, String arg2) {
                        callback.failure(arg0,arg1,arg2);
                    }

                });
            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
    }

    @Override
    public void getPositionInfo(DLNAMediaManagerCallback.Value callback) {
        try {
            Service localService = currentDevice.findService(new UDAServiceId("AVTransport"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new GetPositionInfo(localService) {
                    @Override
                    public void received(ActionInvocation invocation, PositionInfo positionInfo) {
                        callback.received(invocation, transformedFrom(positionInfo.getAbsTime()));
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        callback.failure(invocation, operation, defaultMsg);
                    }
                });
            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

    }

    @Override
    public void getVolume(DLNAMediaManagerCallback.Value callback) {
        try {
            Service localService = currentDevice.findService(new UDAServiceId("RenderingControl"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new GetVolume(localService) {
                    @Override
                    public void received(ActionInvocation actionInvocation, int currentVolume) {
                        callback.received(actionInvocation, currentVolume);
                    }

                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        callback.failure(invocation, operation, defaultMsg);
                    }
                });
            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }

    }

    @Override
    public void setVolume(long value, DLNAMediaManagerCallback.OneWay callback) {
        try {
            Service localService = currentDevice.findService(new UDAServiceId("RenderingControl"));
            if (localService != null) {
                upnpService.getControlPoint().execute(new SetVolume(localService, value) {
                    @Override
                    public void failure(ActionInvocation invocation, UpnpResponse operation, String defaultMsg) {
                        callback.failure(invocation, operation, defaultMsg);
                    }
                });
            } else {
                Log.e("null", "null");
            }
        } catch (Exception localException) {
            localException.printStackTrace();
        }
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
    private String transformedFrom(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter fmt = new Formatter(stringBuilder);
        fmt.format("%02d:%02d:%02d", hour, minute, second);
        return stringBuilder.toString();
    }
    private long transformedFrom(String timeInterval){
        String [] timeComponent = timeInterval.split(":");
        if (timeComponent.length == 3) {
            int hour = Integer.valueOf(timeComponent[0]);
            int minute = Integer.valueOf(timeComponent[1]);
            int second = Integer.valueOf(timeComponent[2]);
            return (long) (hour*60*60 + minute*60 + second);
        }
        else
        {
            return 0;
        }

    }
}
