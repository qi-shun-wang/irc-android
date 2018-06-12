package com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.KaraokeFavoriteCollection.KaraokeFavoriteCollectionContracts.InteractorOutput;

/**
 * Created by shun on 2018/6/11 下午 02:55:45.
 * .
 */

public class KaraokeFavoriteCollectionInteractor implements KaraokeFavoriteCollectionContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    KaraokeFavoriteCollectionInteractor(Context context) {
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

