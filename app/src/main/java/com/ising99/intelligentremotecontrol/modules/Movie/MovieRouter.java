package com.ising99.intelligentremotecontrol.modules.Movie;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.View;

/**
 * Created by shun on 2018/4/13 下午 05:18:02.
 * .
 */

public class MovieRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    public MovieRouter(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        context = null;
        presenter = null;
    }
}

