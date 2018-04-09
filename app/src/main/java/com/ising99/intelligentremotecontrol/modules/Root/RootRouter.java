package com.ising99.intelligentremotecontrol.modules.Root;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryActivity;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Wireframe;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootRouter implements Wireframe {

    private Context context;

    RootRouter(Context context) {
        this.context = context;
    }
    @Override
    public void decompose() {
        context = null;
    }

    @Override
    public void openDeviceDiscovery() {
        Intent intentToDeviceDiscoveryActivity = new Intent(context, DeviceDiscoveryActivity.class);
        intentToDeviceDiscoveryActivity.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentToDeviceDiscoveryActivity);
    }

}
