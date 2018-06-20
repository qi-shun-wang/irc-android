package com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.Wireframe;

/**
 * Created by shun on 2018/6/20 下午 02:35:23.
 * .
 */

public class MediaShareNavWrapperPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    MediaShareNavWrapperPresenter() {
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
        router.createRootFragment();
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
