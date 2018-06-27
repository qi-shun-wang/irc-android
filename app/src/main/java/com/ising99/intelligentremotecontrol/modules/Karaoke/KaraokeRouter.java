package com.ising99.intelligentremotecontrol.modules.Karaoke;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.BaseNavigator;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.View;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderFragment;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderRouter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionFragment;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionRouter;

/**
 * Created by shun on 2018/4/17 下午 10:03:05.
 * .
 */

public class KaraokeRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private BaseNavigator navigator;

    private KaraokeRouter(Context context) {
        this.context = context;
    }

    public static KaraokeFragment setupModule(Context context, BaseNavigator navigator) {

        KaraokeFragment view = new KaraokeFragment();
        KaraokeInteractor interactor = new KaraokeInteractor(context);
        KaraokePresenter presenter = new KaraokePresenter();
        KaraokeRouter router = new KaraokeRouter(context);

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
    public void presentKaraokeFavoriteCollection() {
        KaraokeFavoriteCollectionFragment favorite = KaraokeFavoriteCollectionRouter.setupModule(context, navigator);
        navigator.push(favorite);
    }

    @Override
    public void presentKaraokeArtistFinder() {
        KaraokeArtistFinderFragment favorite = KaraokeArtistFinderRouter.setupModule(context, navigator);
        navigator.push(favorite);
    }
}

