package com.ising99.intelligentremotecontrol.modules.More;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.View;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.More.MoreContracts.Wireframe;

/**
 * Created by shun on 2018/4/13 下午 05:17:20.
 * .
 */

public class MorePresenter implements Presenter, InteractorOutput {

    private Context context;
    private View view;
    private Interactor interactor;
    private Wireframe router;

    MorePresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        interactor = new MoreInteractor(context, this);
        router = new MoreRouter(context, this);

    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        interactor.decompose();
        router.decompose();
        context = null;
        view = null;
        interactor = null;
        router = null;
    }


    //TODO: Implement your Presenter methods here

    //TODO: Implement your InteractorOutput methods here

}
