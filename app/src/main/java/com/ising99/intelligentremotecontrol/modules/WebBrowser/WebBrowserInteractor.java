package com.ising99.intelligentremotecontrol.modules.WebBrowser;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/17 下午 10:05:57.
 * .
 */

public class WebBrowserInteractor implements WebBrowserContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    WebBrowserInteractor(Context context) {
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

