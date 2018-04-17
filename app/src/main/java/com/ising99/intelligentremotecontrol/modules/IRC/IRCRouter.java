package com.ising99.intelligentremotecontrol.modules.IRC;

import android.app.Fragment;
import android.content.Context;

import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private Fragment view ;

    private IRCRouter(Context context) {
        this.context = context;
    }


    public static IRCFragment setupModule(Context context, RemoteControlCoAPService service){

        IRCFragment view = new IRCFragment();
        IRCInteractor interactor = new IRCInteractor(context, service);
        IRCPresenter presenter = new IRCPresenter();
        IRCRouter router = new IRCRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;

        interactor.setupPresenter(presenter);

        return view;
    }
}

