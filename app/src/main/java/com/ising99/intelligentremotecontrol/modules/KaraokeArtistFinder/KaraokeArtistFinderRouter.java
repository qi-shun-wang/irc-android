package com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.View;

/**
 * Created by shun on 2018/6/7 上午 09:33:08.
 * .
 */

public class KaraokeArtistFinderRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public KaraokeArtistFinderRouter(Context context) {
        this.context = context;
    }

    public static KaraokeArtistFinderFragment setupModule(Context context) {

        KaraokeArtistFinderFragment view = new KaraokeArtistFinderFragment();
        KaraokeArtistFinderInteractor interactor = new KaraokeArtistFinderInteractor(context);
        KaraokeArtistFinderPresenter presenter = new KaraokeArtistFinderPresenter();
        KaraokeArtistFinderRouter router = new KaraokeArtistFinderRouter(context);

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

