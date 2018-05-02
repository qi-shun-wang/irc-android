package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaShareInteractor implements MediaShareContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    MediaShareInteractor(Context context) {
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

