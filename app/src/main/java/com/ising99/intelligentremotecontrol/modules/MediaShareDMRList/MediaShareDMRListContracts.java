package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import org.fourthline.cling.model.meta.Device;

import java.util.List;

/**
 * Created by shun on 2018/5/4 上午 10:51:23.
 * .
 */

public interface MediaShareDMRListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadDMRList(List<Device> devices);
        void updateSearchedStatus(String text);
        void startSearchIconRotation();
        void stopSearchIconRotation();
    }

    interface Presenter extends BaseContracts.Presenter {
        void prepareCastDeviceAt(int index);
        void performDismiss();
        void performRefreshTask();
    }

    interface Interactor extends BaseContracts.Interactor {
        void startSearchDMR();
        void stopSearchDMR();
        List<Device> getCurrentDevices();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void dismissDMRList();
    }
}
