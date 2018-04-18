package com.ising99.intelligentremotecontrol.modules.WebBookmark;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.InteractorOutput;

/**
 * Created by shun on 2018/4/17 下午 10:59:43.
 * .
 */

public class WebBookmarkInteractor implements WebBookmarkContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    WebBookmarkInteractor(Context context) {
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

