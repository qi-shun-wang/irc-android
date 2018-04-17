package com.ising99.intelligentremotecontrol.modules;

import java.io.Serializable;

public interface  BaseContracts {

    interface View extends Serializable {
        void setupPresenter(Presenter presenter);
        void decompose();
    }

    interface Presenter extends Serializable {
        void onCreate();
        void onPause();
        void onResume();
        void onDestroy();

        void setupView(View view);
        void setupInteractor(Interactor interactor);
        void setupWireframe(Wireframe router);
        void decompose();
    }

    interface Interactor extends Serializable {
        void setupPresenter(InteractorOutput output);
        void decompose();
    }

    interface InteractorOutput extends Serializable {

    }

    interface Wireframe extends Serializable {
    }
}
