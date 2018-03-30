package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface RootContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void updateNetworkStatus(String text);
        void updateConnectedDeviceStatus(String text);
    }

    interface Presenter extends BaseContracts.Presenter {
        void didTapOnDeviceDiscovery();
        void didSend(KeyCode code);
    }

    interface Interactor extends BaseContracts.Interactor {
        void perform(KeyCode code);
        void checkWiFiStatus();
        void checkLastConnectedDevice();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didSended();
        void didConnectedToWiFi(String name);
        void didNotConnectedToWiFi();
        void didConnectedToDevice(Device device);
        void failure(String msg);

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void openDeviceDiscovery();
    }
}
