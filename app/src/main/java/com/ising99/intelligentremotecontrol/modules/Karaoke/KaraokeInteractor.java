package com.ising99.intelligentremotecontrol.modules.Karaoke;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/13 下午 05:16:29.
 * .
 */

public class KaraokeInteractor implements KaraokeContracts.Interactor {

    private Context context;
    private InteractorOutput output;

    KaraokeInteractor(Context context, InteractorOutput output) {
        this.context = context;
        this.output = output;
    }

    @Override
    public void decompose() {
        context = null;
        output = null;
    }
}

