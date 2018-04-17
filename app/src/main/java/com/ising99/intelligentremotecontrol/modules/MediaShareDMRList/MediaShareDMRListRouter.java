package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.content.Context;
import android.content.Intent;

import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.View;

/**
 * Created by shun on 2018/4/10 上午 10:33:06.
 */

public class MediaShareDMRListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;

    public MediaShareDMRListRouter(Context context, Presenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }


}

