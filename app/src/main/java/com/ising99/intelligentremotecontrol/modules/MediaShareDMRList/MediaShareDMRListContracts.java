package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.List;

/**
 * Created by shun on 2018/4/10 上午 10:33:06.
 */

public interface MediaShareDMRListContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void reloadDMRList(List<RemoteDevice> devices);
    }

    interface Presenter extends BaseContracts.Presenter {

    }

    interface Interactor extends BaseContracts.Interactor {
        void startSearchDMR();
        void stopSearchDMR();
        List<RemoteDevice> getCurrentDevices();
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}
