package com.ising99.intelligentremotecontrol.modules.ScreenSharing;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.InteractorOutput;

/**
 * Created by shun on 2018/6/26 上午 11:34:28.
 * .
 */

public class ScreenSharingInteractor implements ScreenSharingContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    ScreenSharingInteractor(Context context) {
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

