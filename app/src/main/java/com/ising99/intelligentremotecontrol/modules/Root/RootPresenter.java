package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.View;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Wireframe;


/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootPresenter implements Presenter ,InteractorOutput{

    private View view;
    private Interactor interactor;
    private Wireframe router;

    RootPresenter(){}

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View)view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor)interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe)router;
    }

    @Override
    public void decompose() {
        interactor.decompose();
        view.decompose();
        interactor = null;
        view = null;
        router = null;
    }

    @Override
    public void onCreate() {
        view.prepareTabBar();
        router.presentTabAt(0);
        view.setupActionBinding();
        view.updateNetworkStatus("尚未連接WiFi");
        view.updateConnectedDeviceStatus("尚未連接設備");
    }

    @Override
    public void onResume() {
        interactor.checkWiFiStatus();
        interactor.checkLastConnectedDevice();
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void didTapOnDeviceDiscovery() {
        router.openDeviceDiscovery();
    }

    @Override
    public boolean didSelectedTabAt(int position) {
        router.presentTabAt(position);
        return true;
    }

    @Override
    public void didConnectedToWiFi(String name) {
        view.updateNetworkStatus("目前連到Wifi " + name);
    }

    @Override
    public void didNotConnectedToWiFi() {
        view.updateNetworkStatus("尚未連接WiFi");
    }

    @Override
    public void didConnectedToDevice(Device device) {
        view.updateConnectedDeviceStatus("目前已連到設備 " + device.getName());
    }

    @Override
    public void openFullScreenMode() {
        view.hideNavigationBar();
        view.hideTabBar();
    }

    @Override
    public void closeFullScreenMode() {
        view.showNavigationBar();
        view.showTabBar();
    }
}
