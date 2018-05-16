package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/5/16 上午 11:55:30.
 * .
 */

public interface MediaShareMusicPlayerPanelContracts extends BaseContracts {

    interface View extends BaseContracts.View {

    }

    interface Presenter extends BaseContracts.Presenter {
        void dismissPanel();
    }

    interface Interactor extends BaseContracts.Interactor {

    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void dismissPanel();
    }
}
