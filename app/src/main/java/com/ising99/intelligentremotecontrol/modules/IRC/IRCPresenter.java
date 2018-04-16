package com.ising99.intelligentremotecontrol.modules.IRC;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.core.CoapClient.SendCode;
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

    private Context context;
    private View view;
    private Interactor interactor;
    private Wireframe router;

    IRCPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        interactor = new IRCInteractor(context, this);
        router = new IRCRouter(context, this);

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
        interactor.decompose();
        router.decompose();
        context = null;
        view = null;
        interactor = null;
        router = null;
    }

    @Override
    public void didSend(SendCode code) {
        interactor.perform(code);
    }

    @Override
    public void didSend(int number) {
        switch (number)
        {
            case 0:interactor.perform(SendCode.KEYCODE_0);break;
            case 1:interactor.perform(SendCode.KEYCODE_1);break;
            case 2:interactor.perform(SendCode.KEYCODE_2);break;
            case 3:interactor.perform(SendCode.KEYCODE_3);break;
            case 4:interactor.perform(SendCode.KEYCODE_4);break;
            case 5:interactor.perform(SendCode.KEYCODE_5);break;
            case 6:interactor.perform(SendCode.KEYCODE_6);break;
            case 7:interactor.perform(SendCode.KEYCODE_7);break;
            case 8:interactor.perform(SendCode.KEYCODE_8);break;
            case 9:interactor.perform(SendCode.KEYCODE_9);break;
        }
    }

    @Override
    public void didSend(String text) {
        interactor.perform(text);
    }

    @Override
    public void didSended() {

    }

    @Override
    public void failure(String msg) {

    }

}
