package com.ising99.intelligentremotecontrol.modules.WebBrowser;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/13 下午 05:15:24.
 * .
 */

public class WebBrowserInteractor implements WebBrowserContracts.Interactor {

    private Context context;
    private InteractorOutput output;

    WebBrowserInteractor(Context context, InteractorOutput output) {
        this.context = context;
        this.output = output;
    }

    @Override
    public void decompose() {
        context = null;
        output = null;
    }
}

