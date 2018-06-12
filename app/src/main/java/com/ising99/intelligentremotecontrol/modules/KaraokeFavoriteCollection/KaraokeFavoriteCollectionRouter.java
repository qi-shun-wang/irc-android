package com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.View;

/**
 * Created by shun on 2018/6/11 下午 02:55:45.
 * .
 */

public class KaraokeFavoriteCollectionRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public KaraokeFavoriteCollectionRouter(Context context) {
        this.context = context;
    }

    public static KaraokeFavoriteCollectionFragment setupModule(Context context) {

        KaraokeFavoriteCollectionFragment view = new KaraokeFavoriteCollectionFragment();
        KaraokeFavoriteCollectionInteractor interactor = new KaraokeFavoriteCollectionInteractor(context);
        KaraokeFavoriteCollectionPresenter presenter = new KaraokeFavoriteCollectionPresenter();
        KaraokeFavoriteCollectionRouter router = new KaraokeFavoriteCollectionRouter(context);

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

