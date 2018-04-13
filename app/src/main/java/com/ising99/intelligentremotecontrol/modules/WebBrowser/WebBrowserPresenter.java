package com.ising99.intelligentremotecontrol.modules.WebBrowser;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.View;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Wireframe;

/**
 * Created by shun on 2018/4/13 下午 05:15:24.
 * .
 */

public class WebBrowserPresenter implements Presenter, InteractorOutput {

    private Context context;
    private View view;
    private Interactor interactor;
    private Wireframe router;

    WebBrowserPresenter(Context context, View view) {
        this.context = context;
        this.view = view;
        interactor = new WebBrowserInteractor(context, this);
        router = new WebBrowserRouter(context, this);

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
