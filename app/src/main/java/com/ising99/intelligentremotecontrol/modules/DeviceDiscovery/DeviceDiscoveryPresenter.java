package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.util.Log;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/23 下午 02:25:33.
 * .
 */

public class DeviceDiscoveryPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private ArrayList<Device> devices = new ArrayList<>();
    private Timer worker;

    private int[] res_scan_id = {
            R.drawable.scan1,
            R.drawable.scan2,
            R.drawable.scan3,
            R.drawable.scan4,
            R.drawable.scan5,
            R.drawable.scan6,
            R.drawable.scan7,
            R.drawable.scan8,
            R.drawable.scan9,
            R.drawable.scan10,
            R.drawable.scan11,
            R.drawable.scan12,
            R.drawable.scan13,
    };
    private int[] res_line_id = {
            R.drawable.line1,
            R.drawable.line2,
            R.drawable.line3,
            R.drawable.line4,
            R.drawable.line5,
            R.drawable.line6,
            R.drawable.line7,
            R.drawable.line8,
            R.drawable.line9,
            R.drawable.line10
    };

    DeviceDiscoveryPresenter() {
    }

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View) view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor) interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe) router;
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
        view.performInitView();
        view.setupListView();

        view.setupLineAnimation(res_line_id);
        view.setupScanAnimation(res_scan_id);
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
    public void selectDeviceAt(int index, float x, float y, int width, int height) {
        view.updateKODImagePosition(x,y,width,height);
        view.stopScanAnimation();
        if (index == -1) return;
        interactor.persistReceived(devices.get(index));
    }

    @Override
    public void searchAgain() {
        view.hideDeviceNotFound();
        searchDevice();
    }

    @Override
    public void openSetting() {
        router.openWifiSetting();
    }

    @Override
    public void didTapOnClose() {
        interactor.stopDeviceDiscoveryTask();
        worker.cancel();
        router.dismissDeviceDiscovery();
    }

    @Override
    public void onWindowFocusChanged(boolean isFocus) {
        if(isFocus)
        {
            searchDevice();
            interactor.startWireChecker();
        }
        else
        {
            interactor.stopDeviceDiscoveryTask();
            worker.cancel();
            view.stopLineAnimation();
            view.stopScanAnimation();
            view.stopKODAnimation();
            devices.clear();
            view.reloadDeviceCollection(devices);
        }
    }

    @Override
    public void didReceived(Device device) {
        Log.d("devices.contains","=====>"+devices.contains(device));
        if (devices.contains(device)) {
            return;
        }
        Log.d("=didReceived=Name=>",device.getName());
        Log.d("=didReceived=Address=>",device.getAddress());
        Log.d("=didReceived=Settings=>",device.getSettings());
        if (device.getName() == null || device.getAddress() == null || device.getBackupAddress() == null){
            return;
        }
        if (Objects.equals(device.getName(), "")){
            return;
        }
        if (Objects.equals(device.getAddress(), "")){
            return;
        }
        if (Objects.equals(device.getBackupAddress(), "")){
            device.setBackupAddress(device.getAddress());
        }
        devices.add(device);
        view.reloadDeviceCollection(devices);
        Log.d("Device HashSet", "====>" + devices.size());
    }

    @Override
    public void didPersisted(Device device) {
        view.setupKodName(device.getName());
        view.startKODAnimation();
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                view.startLineAnimation();
            }
        }, 2000);

        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                view.stopKODAnimation();
                view.stopLineAnimation();
                view.showConnectionSuccess();
            }
        }, 5000);

        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                router.dismissDeviceDiscovery();
                worker.cancel();
            }
        }, 8000);
    }


    private void searchDevice(){
        worker = new Timer();
        view.hideConnectionFailed();
        view.hideDeviceNotFound();
        view.startScanAnimation();
        interactor.startDeviceDiscoveryTask();
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                check();
            }
        }, 15000);

    }

    private void check() {
        view.stopScanAnimation();
        interactor.stopDeviceDiscoveryTask();
        boolean isWiFiConnected = interactor.checkWiFiStatus();
        if (isWiFiConnected){
            if (devices.size() == 0){
                view.showDeviceNotFound();
            }
//            else {
//                view.reloadDeviceCollection(devices);
//            }
        } else {
            view.showConnectionFailed();
        }
    }
}
