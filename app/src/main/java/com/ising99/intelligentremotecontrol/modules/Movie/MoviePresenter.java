package com.ising99.intelligentremotecontrol.modules.Movie;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.View;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.Wireframe;

/**
 * Created by shun on 2018/4/13 下午 05:18:02.
 * .
 */

public class MoviePresenter implements Presenter, InteractorOutput {

    private Context context;
    private View view;
    private Interactor interactor;
    private Wireframe router;

    MoviePresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        interactor = new MovieInteractor(context, this);
        router = new MovieRouter(context, this);

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


    //TODO: Implement your Presenter methods here

    //TODO: Implement your InteractorOutput methods here

}
