package com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/6/27 上午 09:31:07.
 * .
 */

public interface KaraokeNavWrapperContracts extends BaseContracts {

    interface View extends BaseContracts.View {
    }

    interface Presenter extends BaseContracts.Presenter {

    }

    interface Interactor extends BaseContracts.Interactor {

    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {

    }

    interface Wireframe extends BaseContracts.Wireframe {
        void createRootFragment();
    }
}
