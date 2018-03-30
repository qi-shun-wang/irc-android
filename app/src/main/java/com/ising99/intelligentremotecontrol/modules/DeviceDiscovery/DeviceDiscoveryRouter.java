package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;

/**
 * Created by shun on 2018/3/27.
 *
 */

class DeviceDiscoveryRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    DeviceDiscoveryRouter(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        context = null;
        presenter = null;
    }
}