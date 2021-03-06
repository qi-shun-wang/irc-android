package com.ising99.intelligentremotecontrol.modules.Game;

import com.ising99.intelligentremotecontrol.component.Action;
import com.ising99.intelligentremotecontrol.core.CoapClient.GameCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/4/20 上午 10:50:03.
 * .
 */

public interface GameContracts extends BaseContracts {

    interface View extends BaseContracts.View {
    }

    interface Presenter extends BaseContracts.Presenter {
        void performTouchOn(Action action);
        void performTouchOnBegan(Action action);
        void performTouchOnEnd(Action action);
        void performStartAction();
        void performSelectAction();
        void performXAction();
        void performYAction();
        void performAAction();
        void performBAction();
        void performL1Action();
        void performL2Action();
        void performR1Action();
        void performR2Action();
        void performThumbLeft(int angle,int strength);
        void performThumbRight(int angle,int strength);

    }

    interface Interactor extends BaseContracts.Interactor {
        void fetchGameEventNumber();
        void dispatchGameEvent(GameCode code);
        void dispatchDPad(GameCode code);
        void dispatchDPadBegan(GameCode code);
        void dispatchDPadEnd(GameCode code);
        void dispatchAxis(GameCode code, String value);
    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {
        void didFetchGameEventNumberFailure();
        void didFetchGameEventNumberSuccess();
    }

    interface Wireframe extends BaseContracts.Wireframe {

    }
}
