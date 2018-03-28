package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.core.Device;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface DeviceDiscoveryContracts extends BaseContracts {

    interface View extends BaseContracts.View {
    }

    interface Presenter extends BaseContracts.Presenter {
        void didReceived(Device device);
    }

    interface Interactor extends BaseContracts.Interactor{
        void cachingReceived(Device device);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput{

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void startDeviceDiscoveryService();
        void stopDeviceDiscoveryService();
    }

}

