package com.ising99.intelligentremotecontrol.modules.Game;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Game.GameContracts.View;

/**
 * Created by shun on 2018/4/20 上午 10:50:03.
 * .
 */

public class GameRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public GameRouter(Context context) {
        this.context = context;
    }

    public static GameFragment setupModule(Context context, RemoteControlCoAPService service) {

        GameFragment view = new GameFragment();
        GameInteractor interactor = new GameInteractor(context,service);
        GamePresenter presenter = new GamePresenter();
        GameRouter router = new GameRouter(context);

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

