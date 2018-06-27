package com.ising99.intelligentremotecontrol.modules.ScreenSharing;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.View;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.Wireframe;

/**
 * Created by shun on 2018/6/26 上午 11:34:28.
 * .
 */

public class ScreenSharingPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    ScreenSharingPresenter() {
    }

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View) view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor) interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe) router;
    }

    @Override
    public void decompose() {
        interactor.decompose();
        view.decompose();
        interactor = null;
        view = null;
        router = null;
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

}
