package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.core.Device;

import java.util.ArrayList;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface DeviceDiscoveryContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadDeviceCollection();
        void setupAdapter();
        void setupGridView();

    }

    interface Presenter extends BaseContracts.Presenter {

        int numberOfItem();
        ArrayList<Device> getDevices();
        void selectDeviceAt(int index);
    }

    interface Interactor extends BaseContracts.Interactor{
        void persistReceived(Device device);
        void startDeviceDiscoveryTask();
        void stopDeviceDiscoveryTask();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput{
        void didReceived(Device device);
    }

    interface Wireframe extends BaseContracts.Wireframe {
    }

}

