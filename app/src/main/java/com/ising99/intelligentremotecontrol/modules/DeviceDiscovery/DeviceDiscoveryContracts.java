package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.core.Device;

import java.util.List;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface DeviceDiscoveryContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadDeviceCollection(List<Device> devices);
        void performInitView();
        void setupListView();
        void setupScanAnimation(int[] res_IDs);
        void setupLineAnimation(int[] res_IDs);
        void startScanAnimation();
        void stopScanAnimation();
        void updateKODImagePosition(float x,float y,int width,int height);
        void startKODAnimation();
        void stopKODAnimation();
        void startLineAnimation();
        void stopLineAnimation();
        void setupKodName(String text);
        void showConnectionSuccess();
        void finishActivity();
        void showDeviceNotFound();
        void hideDeviceNotFound();
        void showConnectionFailed();
        void hideConnectionFailed();
    }

    interface Presenter extends BaseContracts.Presenter  {
        void selectDeviceAt(int index,float x, float y, int width, int height);
        void searchAgain();
        void openSetting();
    }

    interface Interactor extends BaseContracts.Interactor {
        void persistReceived(Device device);
        void startDeviceDiscoveryTask();
        void stopDeviceDiscoveryTask();
        void startWireChecker();
        boolean checkWiFiStatus();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput{
        void didReceived(Device device);
        void didPersisted(Device device);
    }

    interface Wireframe extends BaseContracts.Wireframe {
        void openWifiSetting();
    }

}

