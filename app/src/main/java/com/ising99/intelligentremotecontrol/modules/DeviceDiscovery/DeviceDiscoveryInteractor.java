package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.util.Log;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.InteractorOutput;
/**
 * Created by shun on 2018/3/27.
 */

public class DeviceDiscoveryInteractor implements Interactor {

    private InteractorOutput output;

    DeviceDiscoveryInteractor(InteractorOutput output){
        this.output = output;
    }

    @Override
    public void decompose() {
        output = null;
    }

    @Override
    public void cachingReceived(Device device) {

        Log.v("Device model",device.toString());
    }
}
