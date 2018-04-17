package com.ising99.intelligentremotecontrol.modules.More;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/17 下午 10:04:17.
 * .
 */

public class MoreInteractor implements MoreContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    MoreInteractor(Context context) {
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

