package com.ising99.intelligentremotecontrol.modules.Root;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.InteractorOutput;



/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootInteractor implements RootContracts.Interactor {

    private InteractorOutput output;
    private Context context;


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
