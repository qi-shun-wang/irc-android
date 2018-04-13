package com.ising99.intelligentremotecontrol.modules.WebBrowser;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.View;

/**
 * Created by shun on 2018/4/13 下午 05:15:24.
 * .
 */

public class WebBrowserRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    public WebBrowserRouter(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        context = null;
        presenter = null;
    }
}

