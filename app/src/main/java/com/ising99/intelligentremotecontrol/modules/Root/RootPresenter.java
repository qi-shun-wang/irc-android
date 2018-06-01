package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.View;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Wireframe;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootPresenter implements Presenter ,InteractorOutput{

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private Timer worker;
    private boolean isDeviceConnected = false;

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
//        view.updateNetworkStatus("尚未連接WiFi");
        view.setupDisconnectedDeviceImage();
        view.updateConnectedDeviceStatus("尚未連接設備");
        interactor.checkWiFiStatus();

    }

    @Override
    public void onResume() {

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
    public void onWindowFocusChanged(boolean isFocus) {
        if (isFocus)
        {
            worker = new Timer();
            worker .schedule(new TimerTask() {
                @Override
                public void run() {
                    interactor.checkWiFiStatus();
                }
            },1000,2000);

        }
        else
        {
            isDeviceConnected = false;
            worker.cancel();
        }
    }

    @Override
    public void didLastConnectionInvalid() {
        view.updateConnectedDeviceStatus("尚未連接到設備");
    }

    @Override
    public void didConnectedToWiFi(String name) {
        if (isDeviceConnected) {return;}
        view.hideWarningBadge();
        interactor.checkLastConnectedDevice();
        view.updateConnectedDeviceStatus("目前連到Wifi " + name);
    }

    @Override
    public void didNotConnectedToWiFi() {
        isDeviceConnected = false;
        view.showWarningBadge();
        view.updateConnectedDeviceStatus("等待連接WiFi...");
    }

    @Override
    public void didConnectedToDevice(Device device) {
        isDeviceConnected = true;
        view.updateConnectedDeviceStatus("目前已連到設備：" + device.getName());
        view.setupConnectedDeviceImage();
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
