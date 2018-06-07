package com.ising99.intelligentremotecontrol.modules.KaraokeFinder;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.InteractorOutput;

/**
 * Created by shun on 2018/6/7 下午 03:15:28.
 * .
 */

public class KaraokeFinderInteractor implements KaraokeFinderContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    KaraokeFinderInteractor(Context context) {
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

