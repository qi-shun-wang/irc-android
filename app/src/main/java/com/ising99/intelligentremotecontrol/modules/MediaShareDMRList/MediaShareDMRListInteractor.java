package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.InteractorOutput;

import org.fourthline.cling.UpnpService;
import org.fourthline.cling.UpnpServiceImpl;
import org.fourthline.cling.android.AndroidUpnpServiceConfiguration;
import org.fourthline.cling.model.message.header.UDAServiceTypeHeader;
import org.fourthline.cling.model.meta.LocalDevice;
import org.fourthline.cling.model.meta.RemoteDevice;
import org.fourthline.cling.model.types.UDAServiceType;
import org.fourthline.cling.registry.Registry;
import org.fourthline.cling.registry.RegistryListener;

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
        UDAServiceType udaType = new UDAServiceType("AVTransport");
        service.getControlPoint().search(new UDAServiceTypeHeader(udaType));
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

    }

    @Override
    public void remoteDeviceDiscoveryFailed(Registry registry, RemoteDevice device, Exception ex) {

    }

    @Override
    public void remoteDeviceAdded(Registry registry, RemoteDevice device) {
        registry.addDevice(device);

    }

    @Override
    public void remoteDeviceUpdated(Registry registry, RemoteDevice device) {
        if (this.devices.contains(device)){
            return;
        }
        this.devices.add(device);

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

