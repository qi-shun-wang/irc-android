package com.ising99.intelligentremotecontrol.modules.IRC;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public interface IRCContracts extends BaseContracts {

    interface View extends BaseContracts.View {

    }

    interface Presenter extends BaseContracts.Presenter {
        void didSend(SendCode code);
    }

    interface Interactor extends BaseContracts.Interactor {
        void perform(SendCode code);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didSended();
        void failure(String msg);
    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}