package com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.View;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.Wireframe;

/**
 * Created by shun on 2018/6/27 上午 09:31:08.
 * .
 */

public class KaraokeNavWrapperPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    KaraokeNavWrapperPresenter() {
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
