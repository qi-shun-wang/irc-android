package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface RootContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void prepareTabBar();
        void setupActionBinding();
        void updateConnectedDeviceStatus(String text);
        void setupConnectedDeviceImage();
        void setupDisconnectedDeviceImage();
        void hideNavigationBar();
        void showNavigationBar();
        void hideTabBar();
        void showTabBar();
        void showWarningBadge();
        void hideWarningBadge();
    }

    interface Presenter extends BaseContracts.Presenter {
        void didTapOnDeviceDiscovery();
        boolean didSelectedTabAt(int position);
        void onWindowFocusChanged(boolean isFocus);
        void openFullScreenMode();
        void closeFullScreenMode();

    }

    interface Interactor extends BaseContracts.Interactor {
        void checkWiFiStatus();
        void checkLastConnectedDevice();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

        void didConnectedToWiFi(String name);
        void didNotConnectedToWiFi();
        void didConnectedToDevice(Device device);
        void didLastConnectionInvalid();

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void openDeviceDiscovery();
        void presentTabAt(int index);
    }
}
