package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

/**
 * Created by shun on 2018/3/27.
 *
 */

class DeviceDiscoveryRouter implements Wireframe {

    private Context context;

    DeviceDiscoveryRouter(Context context) {
        this.context = context;
    }

    @Override
    public void decompose() {
        context = null;
    }

    @Override
    public void openWifiSetting() {
        Intent intentToWifiSetting = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intentToWifiSetting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentToWifiSetting);
    }
}