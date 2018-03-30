package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.DeviceDiscoveryTask;
import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.DeviceDiscoveryDelegate;
import com.ising99.intelligentremotecontrol.db.DeviceEntity;
import com.ising99.intelligentremotecontrol.db.DeviceEntityDao;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryInteractor implements Interactor, DeviceDiscoveryDelegate {

    private DeviceDiscoveryTask task;
    private InteractorOutput output;
    private Context context;

    DeviceDiscoveryInteractor(Context context, InteractorOutput output){
        this.output = output;
        this.context = context;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
        task.cancel(true);
        task = null;
    }

    @Override
    public void persistReceived(Device device) {
        Log.v("Device model",device.toString());

        Query query = ((App)context).getDaoSession().getDeviceEntityDao().queryBuilder()
                .where(DeviceEntityDao.Properties.Address.ge(device.getAddress()))
                .build();

        List devices = query.list();
        if (devices.size() > 0) {
            //TODO-update
            DeviceEntity entity = (DeviceEntity)devices.get(0);
            entity.setName(device.getName());
            entity.setSettings(device.getName());
            entity.setUpdate_at( new Date());
            ((App)context).getDaoSession().getDeviceEntityDao().update(entity);
            return;
        }

        DeviceEntity entity = new DeviceEntity(device.getAddress(), device.getName(), device.getSettings(), new Date(), new Date());
        ((App)context).getDaoSession().getDeviceEntityDao().insert(entity);

    }

    @Override
    public void startDeviceDiscoveryTask() {
        task = new DeviceDiscoveryTask();
        task.execute(this);
    }

    @Override
    public void stopDeviceDiscoveryTask() {
        if (!task.isCancelled()) {
            task.cancel(true);
        }
    }

    @Override
    public void didRecieved(String message) {
        Device device = new Gson().fromJson(message,Device.class);
        output.didReceived(device);
    }
}
