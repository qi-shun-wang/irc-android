package com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeNavWrapper.KaraokeNavWrapperContracts.InteractorOutput;

/**
 * Created by shun on 2018/6/27 上午 09:31:08.
 * .
 */

public class KaraokeNavWrapperInteractor implements KaraokeNavWrapperContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    KaraokeNavWrapperInteractor(Context context) {
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

