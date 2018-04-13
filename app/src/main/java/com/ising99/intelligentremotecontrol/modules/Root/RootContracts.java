package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface RootContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void prepareTabs();
        void setupActionBinding();
        void updateNetworkStatus(String text);
        void updateConnectedDeviceStatus(String text);
        void replaceCurrentTab(int order);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didTapOnDeviceDiscovery();
        boolean didSelectedTabAt(int position);

    }

    interface Interactor extends BaseContracts.Interactor {
        void checkWiFiStatus();
        void checkLastConnectedDevice();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

        void didConnectedToWiFi(String name);
        void didNotConnectedToWiFi();
        void didConnectedToDevice(Device device);

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void openDeviceDiscovery();
    }
}
