package com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.MediaShareNavWrapperContracts.InteractorOutput;

/**
 * Created by shun on 2018/6/20 下午 02:35:23.
 * .
 */

public class MediaShareNavWrapperInteractor implements MediaShareNavWrapperContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    MediaShareNavWrapperInteractor(Context context) {
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

