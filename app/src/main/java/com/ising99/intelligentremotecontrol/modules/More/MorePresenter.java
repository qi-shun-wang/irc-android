package com.ising99.intelligentremotecontrol.modules.More;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.View;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Wireframe;

/**
 * Created by shun on 2018/4/17 下午 10:04:17.
 * .
 */

public class MorePresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    MorePresenter() {
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
