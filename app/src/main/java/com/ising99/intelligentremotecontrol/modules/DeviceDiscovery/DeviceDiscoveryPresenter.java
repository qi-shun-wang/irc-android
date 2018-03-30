package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

import java.util.ArrayList;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryPresenter implements Presenter ,InteractorOutput {

    private Context context;
    private View view;
    private Interactor interactor;
    private Wireframe router;
    private ArrayList<Device> devices = new ArrayList<>();

    DeviceDiscoveryPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        interactor = new DeviceDiscoveryInteractor(context,this);
        router = new DeviceDiscoveryRouter(context, this);

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
        context = null;
    }

    @Override
    public synchronized void didReceived(Device device) {

        Log.d("devices.contains","=====>"+devices.contains(device));
        if (devices.contains(device)) {
            return;
        }
        Log.d("=didRecieved=Name=>",device.getName());
        Log.d("=didRecieved=Address=>",device.getAddress());
        Log.d("=didRecieved=Settings=>",device.getSettings());
        devices.add(device);
        Log.d("Device HashSet", "====>" + devices.size());
        view.reloadDeviceCollection();

    }

    @Override
    public int numberOfItem() {
        return devices.size();
    }

    @Override
    public ArrayList<Device> getDevices() {
        return devices;
    }

    @Override
    public void selectDeviceAt(int index) {
        interactor.persistReceived(devices.get(index));
    }
}
