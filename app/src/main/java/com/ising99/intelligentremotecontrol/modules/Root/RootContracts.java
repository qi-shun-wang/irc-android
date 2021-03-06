package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface RootContracts extends BaseContracts {

    interface View extends BaseContracts.View {
        void prepareTabBar();
        void hideTabBar();
        void showTabBar();

    }

    interface Presenter extends BaseContracts.Presenter {
        boolean didSelectedTabAt(int position);
    }

    interface Interactor extends BaseContracts.Interactor {

    }

    interface InteractorOutput extends BaseContracts.InteractorOutput {



    }

    interface Wireframe extends BaseContracts.Wireframe {
        void presentTabAt(int index);
    }
}
