package com.ising99.intelligentremotecontrol.modules.Movie;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.View;

/**
 * Created by shun on 2018/4/17 下午 10:05:13.
 * .
 */

public class MovieRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MovieRouter(Context context) {
        this.context = context;
    }

    public static MovieFragment setupModule(Context context) {

        MovieFragment view = new MovieFragment();
        MovieInteractor interactor = new MovieInteractor(context);
        MoviePresenter presenter = new MoviePresenter();
        MovieRouter router = new MovieRouter(context);

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

