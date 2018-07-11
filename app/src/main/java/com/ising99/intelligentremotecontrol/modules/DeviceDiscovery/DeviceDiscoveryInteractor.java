package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.google.gson.Gson;
import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPServiceCallback;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.DeviceDiscoveryDelegate;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.DeviceDiscoveryTask;
import com.ising99.intelligentremotecontrol.db.DeviceEntity;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;

import java.util.Date;
import java.util.List;

/**
 * Created by shun on 2018/5/23 下午 02:25:33.
 * .
 */

public class DeviceDiscoveryInteractor implements DeviceDiscoveryContracts.Interactor ,DeviceDiscoveryDelegate {

    private InteractorOutput output;
    private Context context;
    private DeviceDiscoveryTask task;
    private RemoteControlCoAPService service;

    DeviceDiscoveryInteractor(Context context, RemoteControlCoAPService service) {
        this.context = context;
        this.service = service;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public void persistReceived(Device device) {
        Log.v("Device model",device.toString());
        ((App)context).getDaoSession().getDeviceEntityDao().deleteAll();
        List<DeviceEntity> devices = ((App)context).getDaoSession().getDeviceEntityDao().loadAll();

        boolean isExist = false;
        for (DeviceEntity entity:devices) {
            if(entity.getIsConnected())
            {
                entity.setIsConnected(false);
            }
            if (entity.getAddress().equals(device.getAddress())) {
                isExist = true;
                entity.setIsConnected(true);
                entity.setName(device.getName());
                entity.setSettings(device.getSettings());
            }
            entity.setUpdate_at( new Date());
            ((App)context).getDaoSession().getDeviceEntityDao().update(entity);
        }
        if (!isExist) {
            DeviceEntity entity = new DeviceEntity(device.getAddress(), device.getName(), device.getSettings(),true, new Date(), new Date());
            ((App)context).getDaoSession().getDeviceEntityDao().insert(entity);
        }
        output.didPersisted(device);
    }

    @Override
    public void startDeviceDiscoveryTask() {
        task = new DeviceDiscoveryTask();
        task.execute(this);
    }

    @Override
    public void stopDeviceDiscoveryTask() {
        if (task == null) return;
        if (!task.isCancelled()) {
            task.cancel(true);
        }
    }

    @Override
    public void startWireChecker() {

//        service.checkWireConnection(new RemoteControlCoAPServiceCallback.Common() {
//            @Override
//            public void didSuccessWith(String payload) {
//
//                Device device = new Gson().fromJson(payload, Device.class);
//                output.didReceived(device);
//            }
//
//            @Override
//            public void didFailure() {
//
//            }
//        });
    }

    @Override
    public boolean checkWiFiStatus() {
        return checkWiFiConnectionStatus(context);
    }

    private boolean checkWiFiConnectionStatus(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (manager != null) {
            info = manager.getActiveNetworkInfo();
        }
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI)
        {
            Log.d("NetworkInfo",info.toString());
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public void didReceived(String message) {
        try {
            Device device = new Gson().fromJson(message,Device.class);
            output.didReceived(device);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }
}

