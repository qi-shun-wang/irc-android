package com.ising99.intelligentremotecontrol.modules;

import java.io.Serializable;

public interface  BaseContracts {

    interface View extends Serializable {
    }

    interface Presenter extends Serializable {
        void onCreate();
        void onPause();
        void onResume();
        void onDestroy();
    }

    interface Interactor extends Serializable {
        void decompose();
    }

    interface InteractorOutput extends Serializable {

    }

    interface Wireframe extends Serializable {
        void decompose();
    }
}
