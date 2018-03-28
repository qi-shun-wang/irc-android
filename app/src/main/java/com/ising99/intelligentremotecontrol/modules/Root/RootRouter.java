package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.core.DeviceDiscovery.UDPListenerService;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryActivity;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.View;

/**
 * Created by shun on 2018/3/27.
 */

public class RootRouter implements Wireframe {

    private Activity activity;
    private Presenter presenter;

    RootRouter(View view, Presenter  presenter) {
        this.activity = (Activity) view;
        this.presenter = presenter;
    }
    @Override
    public void decompose() {
        activity = null;
        presenter = null;
    }

    @Override
    public void openDeviceDiscovery() {
        Intent intentToDeviceDiscoveryActivity = new Intent(activity, DeviceDiscoveryActivity.class);
        intentToDeviceDiscoveryActivity.putExtra("DeviceDiscoveryService",UDPListenerService.class);
        activity.startActivity(intentToDeviceDiscoveryActivity);
    }

}
