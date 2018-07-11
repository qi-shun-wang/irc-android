package com.ising99.intelligentremotecontrol.modules.IRC;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPServiceCallback;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.db.DeviceEntity;
import com.ising99.intelligentremotecontrol.db.DeviceEntityDao;

import org.greenrobot.greendao.query.Query;

import java.util.Date;
import java.util.List;
/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCInteractor implements IRCContracts.Interactor {

    private Context context;
    private InteractorOutput output;
    private RemoteControlCoAPService service;

    IRCInteractor(Context context, RemoteControlCoAPService service){
        this(context);
        this.service = service;
    }

    private IRCInteractor(Context context){
        this.context = context;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (IRCContracts.InteractorOutput)output;
    }

    @Override
    public void decompose() {
        context = null;
        output = null;
    }

    @Override
    public void perform(SendCode code) {
        service.send(code);
        output.didSent();
    }

    @Override
    public void performBegan(SendCode code) {
        service.sendBegan(code);
        output.didSent();
    }

    @Override
    public void performEnd(SendCode code) {
        service.sendEnd(code);
        output.didSent();
    }

    @Override
    public void performL(SendCode code) {
        service.sendL(code);
    }

    @Override
    public void perform(String text) {
        service.send(text);
    }

    @Override
    public String getAddress() {
        return service.getAddress();
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
            service.setAddress(device.getAddress());
            output.didConnectedToDevice(new Device(device.getAddress(),device.getAddress(),device.getName(),device.getSettings()));
            Log.d("Device is Connected", device.getName());
//            service.ping(new RemoteControlCoAPServiceCallback.Common() {
//                @Override
//                public void didSuccessWith(String payload) {
//                    output.didConnectedToDevice(new Device(device.getAddress(),device.getAddress(),device.getName(),device.getSettings()));
//                }
//
//                @Override
//                public void didFailure() {
//                    List<DeviceEntity> devices = ((App)context).getDaoSession().getDeviceEntityDao().loadAll();
//
//                    for (DeviceEntity entity:devices) {
//                        entity.setIsConnected(false);
//                        entity.setUpdate_at(new Date());
//                        ((App)context).getDaoSession().getDeviceEntityDao().update(entity);
//                    }
//                    output.didLastConnectionInvalid();
//                }
//            });
//
//        }
        }else{
            output.didLastConnectionInvalid();
        }

    }

}

