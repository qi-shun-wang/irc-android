package com.ising99.intelligentremotecontrol.modules.Root;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPServiceCallback;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.db.DeviceEntity;
import com.ising99.intelligentremotecontrol.db.DeviceEntityDao;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.InteractorOutput;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootInteractor implements RootContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private RemoteControlCoAPService service;

    RootInteractor(Context context, RemoteControlCoAPService service){
        this.context = context;
        this.service = service;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput)output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
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
        }
        else
        {
            output.didNotConnectedToWiFi();
        }
    }

    @Override
    public void checkLastConnectedDevice() {
        Query query = ((App)context).getDaoSession().getDeviceEntityDao().queryBuilder()
                .where(DeviceEntityDao.Properties.IsConnected.ge(true))
                .build();
        List devices = query.list();
        if(devices.size() > 0) {
            DeviceEntity device = (DeviceEntity) devices.get(0);
            Log.d("Device is Connected",device.getName());
            service.setAddress(device.getAddress());
            service.ping(new RemoteControlCoAPServiceCallback.Common() {
                @Override
                public void didSuccessWith(String payload) {
                    output.didConnectedToDevice(new Device(device.getAddress(),device.getAddress(),device.getName(),device.getSettings()));
                }

                @Override
                public void didFailure() {
                    List<DeviceEntity> devices = ((App)context).getDaoSession().getDeviceEntityDao().loadAll();

                    for (DeviceEntity entity:devices) {
                        entity.setIsConnected(false);
                        entity.setUpdate_at(new Date());
                        ((App)context).getDaoSession().getDeviceEntityDao().update(entity);
                    }
                    output.didLastConnectionInvalid();
                }
            });

            return;
        }
        output.didLastConnectionInvalid();
    }

}
