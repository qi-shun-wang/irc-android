package com.ising99.intelligentremotecontrol.modules.IRC;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;

/**
 * Created by shun on 2018/4/12 下午 02:16:54.
 * .
 */

public class IRCRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    IRCRouter(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        context = null;
        presenter = null;
    }
}

