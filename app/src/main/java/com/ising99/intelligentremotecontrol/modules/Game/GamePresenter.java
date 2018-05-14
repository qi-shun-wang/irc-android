package com.ising99.intelligentremotecontrol.modules.Game;

import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.View;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.Wireframe;

/**
 * Created by shun on 2018/4/20 上午 10:50:03.
 * .
 */

public class GamePresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    GamePresenter() {
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

    @Override
    public void performUpAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_UP);
    }

    @Override
    public void performDownAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_DOWN);
    }

    @Override
    public void performLeftAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_LEFT);
    }

    @Override
    public void performRightAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_RIGHT);
    }

    @Override
    public void performStartAction() {

    }

    @Override
    public void performSelectAction() {
        interactor.perform(SendCode.KEYCODE_DPAD_CENTER);
    }

    @Override
    public void performXAction() {

    }

    @Override
    public void performYAction() {

    }

    @Override
    public void performAAction() {

    }

    @Override
    public void performBAction() {

    }
}
