package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.InteractorOutput;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.model.message.header.DeviceTypeHeader;
import org.fourthline.cling.model.message.header.STAllHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/4 上午 10:51:23.
 * .
 */

public class MediaShareDMRListInteractor implements MediaShareDMRListContracts.Interactor ,RegistryListener {

    private InteractorOutput output;
    private Context context;
    private UpnpService service ;
    private ArrayList<RemoteDevice> devices = new ArrayList<>();

    MediaShareDMRListInteractor(Context context) {
        this.context = context;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public void startSearchDMR() {
        service = new UpnpServiceImpl(new AndroidUpnpServiceConfiguration(),this);
        URI path ;
        try {
            path = new URI("urn:schemas-upnp-org:device:MediaRenderer:1");
            service.getControlPoint().search(new DeviceTypeHeader(path));
        }catch (URISyntaxException e){
            service.getControlPoint().search(new STAllHeader(),5);
        }
    }

    @Override
    public void stopSearchDMR() {
        new Thread(() -> {
            if (service != null)
            {
                service.getRegistry().removeListener(this);
                service.shutdown();
            }
        });
    }

    @Override
    public List<RemoteDevice> getCurrentDevices() {
        return devices;
    }

    @Override
    public void remoteDeviceDiscoveryStarted(Registry registry, RemoteDevice device) {
        if (this.devices.contains(device)){
            return;
        }
        this.devices.add(device);
        Log.v("DMR-Device",device.getDetails().toString());
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
        Log.v("DMR-Device","beforeShutdown");
    }

    @Override
    public void afterShutdown() {
        Log.v("DMR-Device","afterShutdown");
    }
}

