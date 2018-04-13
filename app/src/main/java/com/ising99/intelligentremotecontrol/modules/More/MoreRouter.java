package com.ising99.intelligentremotecontrol.modules.More;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.View;

/**
 * Created by shun on 2018/4/13 下午 05:17:20.
 * .
 */

public class MoreRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    public MoreRouter(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        context = null;
        presenter = null;
    }
}

