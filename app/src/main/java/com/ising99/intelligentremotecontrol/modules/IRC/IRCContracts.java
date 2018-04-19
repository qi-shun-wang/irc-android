package com.ising99.intelligentremotecontrol.modules.IRC;

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
    }

    interface Interactor extends BaseContracts.Interactor {
        void perform(SendCode code);
        void perform(String  text);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didSent();
        void failure(String msg);
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

    }
}
