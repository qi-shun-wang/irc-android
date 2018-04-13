package com.ising99.intelligentremotecontrol.modules.IRC;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.db.DeviceEntity;
import com.ising99.intelligentremotecontrol.db.DeviceEntityDao;

import org.greenrobot.greendao.query.Query;

import java.util.List;
/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCInteractor implements IRCContracts.Interactor {

    private Context context;
    private InteractorOutput output;
    private RemoteControlCoAPService service;
    IRCInteractor(Context context, InteractorOutput output) {
        this.context = context;
        this.output = output;
    }

    @Override
    public void perform(SendCode code) {
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

//            output.didConnectedToDevice(new Device(device.getAddress(),device.getName(),device.getSettings()));
        }
        service.send(code);
        output.didSended();
        output.failure("伺服器異常");
    }

    @Override
    public void decompose() {
        context = null;
        output = null;
    }
}
