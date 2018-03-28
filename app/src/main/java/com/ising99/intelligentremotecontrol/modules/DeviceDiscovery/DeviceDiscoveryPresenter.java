package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.util.Log;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryPresenter implements Presenter ,InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    DeviceDiscoveryPresenter(View view) {
        this.view = view;
        interactor = new DeviceDiscoveryInteractor(this);
        router = new DeviceDiscoveryRouter(view,this);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {
        router.startDeviceDiscoveryService();
    }

    @Override
    public void onPause() {
        router.stopDeviceDiscoveryService();
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
    public void didReceived(Device device) {

        interactor.cachingReceived(device);
    }
}
