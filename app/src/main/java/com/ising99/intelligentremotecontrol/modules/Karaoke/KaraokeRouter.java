package com.ising99.intelligentremotecontrol.modules.Karaoke;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.View;

/**
 * Created by shun on 2018/4/13 下午 05:16:29.
 * .
 */

public class KaraokeRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    public KaraokeRouter(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }

    @Override
    public void decompose() {
        context = null;
        presenter = null;
    }
}

