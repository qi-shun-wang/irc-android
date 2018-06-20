package com.ising99.intelligentremotecontrol.modules.IRC;

import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public interface IRCContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void setupActionBinding();
        void updateConnectedDeviceStatus(String text);
        void setupConnectedDeviceImage();
        void setupDisconnectedDeviceImage();
        void showWarningBadge();
        void hideWarningBadge();
    }

    interface Presenter extends BaseContracts.Presenter {
        String getAddress();
        void onWindowFocusChanged(boolean isFocus);
        void didTapOnDeviceDiscovery();

    }

    interface Interactor extends BaseContracts.Interactor {
        void perform(SendCode code);
        void performBegan(SendCode code);
        void performEnd(SendCode code);
        void performL(SendCode code);
        void perform(String  text);
        String getAddress();
        void checkWiFiStatus();
        void checkLastConnectedDevice();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didSent();
        void failure(String msg);
        void didConnectedToWiFi(String name);
        void didNotConnectedToWiFi();
        void didConnectedToDevice(Device device);
        void didLastConnectionInvalid();
    }

    interface Wireframe extends BaseContracts.Wireframe {
        void composingModes();
        void decomposingModes();
        void presentNumPanel();
        void presentMediaPanel();
        void presentModePanel();
        void presentTextingMode();
        void presentTouchMode();
        void presentNormalMode();
        void presentDefaultMode();
        void presentGameMode();
        void dismissNumPanel();
        void dismissMediaPanel();
        void dismissModePanel();
        void openDeviceDiscovery();

    }
}
