package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.app.Activity;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

/**
 * Created by shun on 2018/3/27.
 *
 */

class DeviceDiscoveryRouter implements Wireframe {

    private Activity activity;
    private Presenter presenter;

    DeviceDiscoveryRouter(View view, Presenter presenter) {
        this.activity = (Activity) view;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        activity = null;
        presenter = null;
    }
}