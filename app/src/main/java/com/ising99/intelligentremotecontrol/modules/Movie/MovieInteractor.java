package com.ising99.intelligentremotecontrol.modules.Movie;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/17 下午 10:05:13.
 * .
 */

public class MovieInteractor implements MovieContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    MovieInteractor(Context context) {
        this.context = context;
    }

    @Override
    public void setupPresenter(BaseContracts.InteractorOutput output) {
        this.output = (InteractorOutput) output;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

}

