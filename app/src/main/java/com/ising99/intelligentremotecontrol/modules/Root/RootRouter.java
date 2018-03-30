package com.ising99.intelligentremotecontrol.modules.Root;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryActivity;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    RootRouter(Context context, Presenter  presenter) {
        this.context = context;
        this.presenter = presenter;
    }
    @Override
    public void decompose() {
        context = null;
        presenter = null;
    }

    @Override
    public void openDeviceDiscovery() {
        Intent intentToDeviceDiscoveryActivity = new Intent(context, DeviceDiscoveryActivity.class);
        context.startActivity(intentToDeviceDiscoveryActivity);
    }

}
