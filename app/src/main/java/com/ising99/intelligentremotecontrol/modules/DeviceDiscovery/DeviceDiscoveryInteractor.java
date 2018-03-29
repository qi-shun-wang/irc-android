package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.util.Log;

import com.google.gson.Gson;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.DeviceDiscoveryTask;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.DeviceDiscoveryDelegate;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryInteractor implements Interactor, DeviceDiscoveryDelegate {

    private DeviceDiscoveryTask task;
    private InteractorOutput output;

    DeviceDiscoveryInteractor(InteractorOutput output){
        this.output = output;
    }

    @Override
    public void decompose() {
        output = null;
        task.cancel(true);
        task = null;
    }

    @Override
    public void cachingReceived(Device device) {

        Log.v("Device model",device.toString());
    }

    @Override
    public void startDeviceDiscoveryTask() {
        task = new DeviceDiscoveryTask();
        task.execute(this);
    }

    @Override
    public void stopDeviceDiscoveryTask() {
        if (!task.isCancelled()) {
            task.cancel(true);
        }
    }

    @Override
    public void didRecieved(String message) {
        Device device = new Gson().fromJson(message,Device.class);
        Log.v("====didRecieved====>",message);
        Log.v("=didRecieved=Name=>",device.getName());
        Log.v("=didRecieved=Address=>",device.getAddress());
        Log.v("=didRecieved=Settings=>",device.getSettings());
        output.didReceived(device);
    }
}
