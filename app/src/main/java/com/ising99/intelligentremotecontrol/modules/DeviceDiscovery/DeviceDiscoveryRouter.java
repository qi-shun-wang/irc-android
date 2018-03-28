package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.UDPListenerService;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.UDPListenerServiceDelegate;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;
import com.ising99.intelligentremotecontrol.core.Device;

import java.io.Serializable;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryRouter implements Wireframe ,UDPListenerServiceDelegate ,Serializable {

    private Activity activity;
    private Presenter presenter;
    private Class<?> UDPListenerServiceClass = UDPListenerService.class;

    DeviceDiscoveryRouter(View view, DeviceDiscoveryContracts.Presenter  presenter) {
        this.activity = (Activity) view;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        activity = null;
        presenter = null;
        UDPListenerServiceClass = null;
    }

    @Override
    public void startDeviceDiscoveryService() {
        Intent multicastService = new Intent(activity,UDPListenerServiceClass);
        multicastService.putExtra("Delegation", this);
        activity.startService(multicastService);
    }

    @Override
    public void stopDeviceDiscoveryService() {
        Intent multicastService = new Intent(activity,UDPListenerServiceClass);
        activity.stopService(multicastService);
    }

    @Override
    public void didRecieved(String message) {
        Device device = new Gson().fromJson(message,Device.class);
        Log.v("====didRecieved====>",message);
        Log.v("=didRecieved=Name=>",device.getName());
        Log.v("=didRecieved=Address=>",device.getAddress());
        Log.v("=didRecieved=Settings=>",device.getSettings());
        presenter.didReceived(device);

    }
}
