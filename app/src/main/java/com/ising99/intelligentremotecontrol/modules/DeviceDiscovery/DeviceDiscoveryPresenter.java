package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.util.Log;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

import java.util.HashSet;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryPresenter implements Presenter ,InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private HashSet<Device> devices = new HashSet<>();

    DeviceDiscoveryPresenter(View view) {
        this.view = view;
        interactor = new DeviceDiscoveryInteractor(this);
        router = new DeviceDiscoveryRouter(view, this);

    }

    @Override
    public void onCreate() {
        view.setupGridView();
        view.setupAdapter();
    }

    @Override
    public void onResume() {
        interactor.startDeviceDiscoveryTask();
    }

    @Override
    public void onPause() {
        interactor.stopDeviceDiscoveryTask();
    }

    @Override
    public void onDestroy() {
        router.decompose();
        interactor.decompose();
        view = null;
        router = null;
        interactor = null;
    }

    @Override
    public synchronized void didReceived(Device device) {

        Log.d("devices.contains","=====>"+devices.contains(device));
        if (devices.contains(device)) {
            return;
        }
        devices.add(device);
        Log.d("Device HashSet", "====>" + devices.size());
        interactor.cachingReceived(device);
        view.reloadDeviceCollection();

    }

    @Override
    public int numberOfItem() {
        return devices.size();
    }

    @Override
    public HashSet<Device> getDevices() {
        return devices;
    }

}
