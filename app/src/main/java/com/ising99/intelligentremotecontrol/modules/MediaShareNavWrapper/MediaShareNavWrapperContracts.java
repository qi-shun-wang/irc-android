package com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/6/20 下午 02:35:22.
 * .
 */

public interface MediaShareNavWrapperContracts extends BaseContracts {

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
