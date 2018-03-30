package com.ising99.intelligentremotecontrol.modules.Root;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.InteractorOutput;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootInteractor implements RootContracts.Interactor {

    private InteractorOutput output;
    private Context context;

    RootInteractor(Context context, InteractorOutput output){
        this.output = output;
        this.context = context;
    }

    @Override
    public void decompose() {
        output = null;
        context = null;
    }

    @Override
    public void perform(KeyCode code) {
        output.didSended();
        output.failure("伺服器異常");
    }
}
