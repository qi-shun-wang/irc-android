package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface RootContracts extends BaseContracts {

    interface View extends BaseContracts.View {
    }

    interface Presenter extends BaseContracts.Presenter {
        void didTapOnDeviceDiscovery();
        void didSend(KeyCode code);
    }

    interface Interactor extends BaseContracts.Interactor {
        void perform(KeyCode code);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void openDeviceDiscovery();
    }
}
