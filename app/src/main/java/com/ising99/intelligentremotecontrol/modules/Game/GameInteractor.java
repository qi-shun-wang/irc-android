package com.ising99.intelligentremotecontrol.modules.Game;

import android.content.Context;
import android.util.Log;

import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.db.DeviceEntity;
import com.ising99.intelligentremotecontrol.db.DeviceEntityDao;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.InteractorOutput;

import org.greenrobot.greendao.query.Query;

import java.util.List;

/**
 * Created by shun on 2018/4/20 上午 10:50:03.
 * .
 */

public class GameInteractor implements GameContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private RemoteControlCoAPService service;

    GameInteractor(Context context) {
        this.context = context;
    }

    GameInteractor(Context context, RemoteControlCoAPService service){
        this(context);
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
    public void perform(SendCode code) {
        Query query = ((App)context).getDaoSession().getDeviceEntityDao().queryBuilder()
                .where(DeviceEntityDao.Properties.IsConnected.ge(true))
                .build();
        List devices = query.list();
        for (Object entity:devices) {
            DeviceEntity device = (DeviceEntity) entity;
            Log.d("Device is Connected",device.getName());
            service.setAddress(device.getAddress());

//            output.didConnectedToDevice(new Device(device.getAddress(),device.getName(),device.getSettings()));
        }
        service.send(code);
    }
}

