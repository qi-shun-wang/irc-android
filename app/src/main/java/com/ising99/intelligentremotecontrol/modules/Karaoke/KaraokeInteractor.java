package com.ising99.intelligentremotecontrol.modules.Karaoke;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/17 下午 10:03:05.
 * .
 */

public class KaraokeInteractor implements KaraokeContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    KaraokeInteractor(Context context) {
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

