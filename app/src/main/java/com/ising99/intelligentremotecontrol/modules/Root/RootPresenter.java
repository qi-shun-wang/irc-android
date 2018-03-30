package com.ising99.intelligentremotecontrol.modules.Root;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.core.Device;
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

    RootPresenter(Context context, View view){
        this.view = view;
        interactor = new RootInteractor(context,this);
        router = new RootRouter(context,this);
    }

    @Override
    public void onCreate() {
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
        interactor.decompose();
        router.decompose();
        view = null;
        interactor = null;
        router = null;
    }

    @Override
    public void didTapOnDeviceDiscovery() {
        router.openDeviceDiscovery();
    }

    @Override
    public void didSend(KeyCode code) {
        interactor.perform(code);
    }

    @Override
    public void didSended() {
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
    public void failure(String msg) {
    }
}
