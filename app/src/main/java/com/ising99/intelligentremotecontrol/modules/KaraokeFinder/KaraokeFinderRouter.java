package com.ising99.intelligentremotecontrol.modules.KaraokeFinder;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.BaseNavigator;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.View;

/**
 * Created by shun on 2018/6/7 下午 03:15:28.
 * .
 */

public class KaraokeFinderRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private BaseNavigator navigator;

    private KaraokeFinderRouter(Context context) {
        this.context = context;
    }

    public static KaraokeFinderFragment setupModule(Context context, BaseNavigator navigator) {

        KaraokeFinderFragment view = new KaraokeFinderFragment();
        KaraokeFinderInteractor interactor = new KaraokeFinderInteractor(context);
        KaraokeFinderPresenter presenter = new KaraokeFinderPresenter();
        KaraokeFinderRouter router = new KaraokeFinderRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;
        router.navigator = navigator;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void navigateBack() {
        navigator.pop();
    }
}

