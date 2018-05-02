package com.ising99.intelligentremotecontrol.modules.MediaShare;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Wireframe;

/**
 * Created by shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaSharePresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    MediaSharePresenter() {
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
