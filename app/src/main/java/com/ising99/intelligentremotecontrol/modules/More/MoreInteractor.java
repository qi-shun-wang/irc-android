package com.ising99.intelligentremotecontrol.modules.More;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/13 下午 05:17:20.
 * .
 */

public class MoreInteractor implements MoreContracts.Interactor {

    private Context context;
    private InteractorOutput output;

    MoreInteractor(Context context, InteractorOutput output) {
        this.context = context;
        this.output = output;
    }

    @Override
    public void decompose() {
        context = null;
        output = null;
    }
}

