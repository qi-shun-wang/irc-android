package com.ising99.intelligentremotecontrol.modules.Root;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.db.DeviceEntity;
import com.ising99.intelligentremotecontrol.db.DeviceEntityDao;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.InteractorOutput;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootInteractor implements RootContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private RemoteControlCoAPService service;
    RootInteractor(Context context, InteractorOutput output){
        this.output = output;
        this.context = context;

    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public void perform(KeyCode code) {
        service.send(code);
        output.didSended();
        output.failure("伺服器異常");
    }

    @Override
    public void checkWiFiStatus() {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = null;
        if (manager != null) {
            info = manager.getActiveNetworkInfo();
        }
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI)
        {
            Log.d("NetworkInfo",info.toString());
            output.didConnectedToWiFi(info.getExtraInfo());
            //TODO-check out persisted device in db
            Query query = ((App)context).getDaoSession().getDeviceEntityDao().queryBuilder()
                    .where(DeviceEntityDao.Properties.IsConnected.ge(true))
                    .build();
            List devices = query.list();
            for (Object entity:devices) {
                DeviceEntity device = (DeviceEntity) entity;
                Log.d("Device is Connected",device.getName());
                if (service == null ){
                    service = new RemoteControlCoAPService(device.getAddress(),5683);
                }
                service.setAddress(device.getAddress());

             output.didConnectedToDevice(new Device(device.getAddress(),device.getName(),device.getSettings()));
            }
        }
        else
        {
            output.didNotConnectedToWiFi();
        }
    }

    @Override
    public void checkLastConnectedDevice() {

    }
}
