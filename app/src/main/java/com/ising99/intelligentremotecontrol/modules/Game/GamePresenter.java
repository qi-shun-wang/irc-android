package com.ising99.intelligentremotecontrol.modules.Game;

import android.util.Log;

import com.ising99.intelligentremotecontrol.component.Action;
import com.ising99.intelligentremotecontrol.core.CoapClient.GameCode;
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
        interactor.fetchGameEventNumber();
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
    public void performTouchOn(Action action) {
        switch (action){
            case up:interactor.dispatchDPad(GameCode.DPAD_UP);break;
            case down:interactor.dispatchDPad(GameCode.DPAD_DOWN);break;
            case right:interactor.dispatchDPad(GameCode.DPAD_RIGHT);break;
            case left:interactor.dispatchDPad(GameCode.DPAD_LEFT);break;
            case center:break;
        }
    }

    @Override
    public void performTouchOnBegan(Action action) {
        switch (action){
            case up:interactor.dispatchDPadBegan(GameCode.DPAD_UP);break;
            case down:interactor.dispatchDPadBegan(GameCode.DPAD_DOWN);break;
            case right:interactor.dispatchDPadBegan(GameCode.DPAD_RIGHT);break;
            case left:interactor.dispatchDPadBegan(GameCode.DPAD_LEFT);break;
            case center:break;
        }
    }

    @Override
    public void performTouchOnEnd(Action action) {
        switch (action){
            case up:interactor.dispatchDPadEnd(GameCode.DPAD_UP);break;
            case down:interactor.dispatchDPadEnd(GameCode.DPAD_DOWN);break;
            case right:interactor.dispatchDPadEnd(GameCode.DPAD_RIGHT);break;
            case left:interactor.dispatchDPadEnd(GameCode.DPAD_LEFT);break;
            case center:break;
        }
    }

    @Override
    public void performStartAction() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_START);
    }

    @Override
    public void performSelectAction() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_SELECT);
    }

    @Override
    public void performXAction() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_X);
    }

    @Override
    public void performYAction() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_Y);
    }

    @Override
    public void performAAction() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_A);
    }

    @Override
    public void performBAction() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_B);
    }

    @Override
    public void performL1Action() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_L1);
    }

    @Override
    public void performL2Action() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_L2);
    }

    @Override
    public void performR1Action() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_R1);
    }

    @Override
    public void performR2Action() {
        interactor.dispatchGameEvent(GameCode.KEYCODE_BUTTON_R2);
    }

    @Override
    public void performThumbLeft(int angle, int strength) {
        double x = strength*1000*Math.cos(Math.PI*angle/180);
        double y = -strength*1000*Math.sin(Math.PI*angle/180);
        interactor.dispatchAxis(GameCode.THUMB_L_AXIS_X,""+x);
        interactor.dispatchAxis(GameCode.THUMB_L_AXIS_Y,""+y);
        Log.d("Joystick",angle + " degree," + strength + " dl" + ",x=" +x+ ",y=" +y);
    }

    @Override
    public void performThumbRight(int angle, int strength) {
        double x = strength*1000*Math.cos(Math.PI*angle/180);
        double y = -strength*1000*Math.sin(Math.PI*angle/180);
        interactor.dispatchAxis(GameCode.THUMB_R_AXIS_X,""+x);
        interactor.dispatchAxis(GameCode.THUMB_R_AXIS_Y,""+y);
        Log.d("Joystick",angle + " degree," + strength + " dl" + ",x=" +x+ ",y=" +y);
    }

    @Override
    public void didFetchGameEventNumberFailure() {

    }

    @Override
    public void didFetchGameEventNumberSuccess() {

    }
}
