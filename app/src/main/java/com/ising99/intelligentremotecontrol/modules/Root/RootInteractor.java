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

    RootInteractor(Context context){
        this.context = context;
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



}
