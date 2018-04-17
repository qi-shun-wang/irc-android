package com.ising99.intelligentremotecontrol.modules.IRC;

import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.View;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Wireframe;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;

    IRCPresenter(){}

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (IRCContracts.View)view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (IRCContracts.Interactor)interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (IRCContracts.Wireframe)router;
    }

    @Override
    public void decompose() {

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
    public void didSent(SendCode code) {
        interactor.perform(code);
    }

    @Override
    public void didSent(int number) {
        interactor.perform(SendCode.numberConvert(number));
    }

    @Override
    public void didSent(String text) {
        interactor.perform(text);
    }

    @Override
    public void didSent() {

    }

    @Override
    public void failure(String msg) {

    }

}
