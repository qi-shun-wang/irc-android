package com.ising99.intelligentremotecontrol.modules.Game;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.CoapClient.GameCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPServiceCallback;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/20 上午 10:50:03.
 * .
 */

public class GameInteractor implements GameContracts.Interactor {

    private InteractorOutput output;
    private Context context;
    private RemoteControlCoAPService service;

    GameInteractor(Context context) {
        this.context = context;
    }

    GameInteractor(Context context, RemoteControlCoAPService service){
        this(context);
        this.service = service;
    }
    private String eventNumber = "2";

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public void perform(SendCode code) {
        service.send(code);
    }

    @Override
    public void performBegan(SendCode code) {
        service.sendBegan(code);
    }

    @Override
    public void performEnd(SendCode code) {
        service.sendEnd(code);
    }

    @Override
    public void fetchGameEventNumber() {
        service.detectGameEventNumber(new RemoteControlCoAPServiceCallback.Common() {
            @Override
            public void didSuccessWith(String payload) {
                eventNumber = payload;
                output.didFetchGameEventNumberSuccess();
            }

            @Override
            public void didFailure() {
                output.didFetchGameEventNumberFailure();
            }
        });
    }

    @Override
    public void dispatchGameEvent(GameCode code) {
        service.sendGameEvent(eventNumber, code);
    }

    @Override
    public void dispatchDPad(GameCode code) {
        service.sendGameDPadEvent(eventNumber, code);
    }

    @Override
    public void dispatchDPadBegan(GameCode code) {
        service.sendGameDPadEventBegan(eventNumber, code);
    }

    @Override
    public void dispatchDPadEnd(GameCode code) {
        service.sendGameDPadEventEnd(eventNumber, code);
    }


}

