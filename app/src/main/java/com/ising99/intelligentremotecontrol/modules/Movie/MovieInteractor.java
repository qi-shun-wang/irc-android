package com.ising99.intelligentremotecontrol.modules.Movie;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/13 下午 05:18:02.
 * .
 */

public class MovieInteractor implements MovieContracts.Interactor {

    private Context context;
    private InteractorOutput output;

    MovieInteractor(Context context, InteractorOutput output) {
        this.context = context;
        this.output = output;
    }

    @Override
    public void decompose() {
        context = null;
        output = null;
    }
}

