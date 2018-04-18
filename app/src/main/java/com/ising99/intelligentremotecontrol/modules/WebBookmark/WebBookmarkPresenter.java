package com.ising99.intelligentremotecontrol.modules.WebBookmark;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.View;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.Wireframe;

/**
 * Created by shun on 2018/4/17 下午 10:59:43.
 * .
 */

public class WebBookmarkPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    WebBookmarkPresenter() {
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
