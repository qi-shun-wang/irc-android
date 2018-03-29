package com.ising99.intelligentremotecontrol.modules.Root;

import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.InteractorOutput;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootInteractor implements RootContracts.Interactor {

    private InteractorOutput output;

    RootInteractor(InteractorOutput output){
        this.output = output;
    }

    @Override
    public void decompose() {
        output = null;
    }

    @Override
    public void perform(KeyCode code) {
        output.didSended();
        output.failure("伺服器異常");
    }
}
