package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryPresenter implements Presenter ,InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private ArrayList<Device> devices = new ArrayList<>();

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

    DeviceDiscoveryPresenter(Context context, View view) {
        this.view = view;
        interactor = new DeviceDiscoveryInteractor(context,this);
        router = new DeviceDiscoveryRouter(context);
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
        searchDevice();
    }

    @Override
    public void onPause() {
        view.stopScanAnimation();
        view.stopKODAnimation();
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
        Log.d("=didRecieved=Name=>",device.getName());
        Log.d("=didRecieved=Address=>",device.getAddress());
        Log.d("=didRecieved=Settings=>",device.getSettings());
        if (device.getName() == null || device.getAddress() == null || device.getBackupAddress() == null){
            return;
        }
        if (Objects.equals(device.getName(), "") || Objects.equals(device.getBackupAddress(), "")){
            return;
        }
        devices.add(device);
        Log.d("Device HashSet", "====>" + devices.size());

    }

    @Override
    public void selectDeviceAt(int index,float x, float y, int width, int height) {
        view.updateKODImagePosition(x,y,width,height);
        interactor.persistReceived(devices.get(index));
    }

    @Override
    public void didPersisted(Device device) {
        view.setupKodName(device.getName());
        view.startKODAnimation();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                view.startLineAnimation();
            }
        }, 2000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //Todo view show success
                view.stopKODAnimation();
                view.stopLineAnimation();
                view.showConnectionSuccess();
            }
        }, 5000);

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                //Todo finish activity
                view.finishActivity();
            }
        }, 8000);
    }

    @Override
    public void searchAgain() {
        view.hideDeviceNotFound();
        searchDevice();
    }

    private void searchDevice(){
        view.hideConnectionFailed();
        view.hideDeviceNotFound();
        view.startScanAnimation();
        interactor.startDeviceDiscoveryTask();
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (view != null && interactor != null ){
                    check();
                }

            }
        }, 5000);
    }

    private void check() {
        view.stopScanAnimation();
        interactor.stopDeviceDiscoveryTask();
        boolean isWiFiConnected = interactor.checkWiFiStatus();
        if (isWiFiConnected){
            if (devices.size()>0){
                view.reloadDeviceCollection(devices);
            } else {
                view.showDeviceNotFound();
            }
        } else {
            view.showConnectionFailed();
        }
    }

    @Override
    public void openSetting() {
        router.openWifiSetting();
    }
}
