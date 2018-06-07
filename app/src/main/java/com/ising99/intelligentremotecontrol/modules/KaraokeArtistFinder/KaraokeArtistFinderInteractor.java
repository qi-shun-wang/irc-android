package com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeArtistFinder.KaraokeArtistFinderContracts.InteractorOutput;

/**
 * Created by shun on 2018/6/7 上午 09:33:08.
 * .
 */

public class KaraokeArtistFinderInteractor implements KaraokeArtistFinderContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    KaraokeArtistFinderInteractor(Context context) {
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

