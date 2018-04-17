package com.ising99.intelligentremotecontrol.modules.WebBrowser;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserContracts.View;

/**
 * Created by shun on 2018/4/17 下午 10:05:57.
 * .
 */

public class WebBrowserRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public WebBrowserRouter(Context context) {
        this.context = context;
    }

    public static WebBrowserFragment setupModule(Context context) {

        WebBrowserFragment view = new WebBrowserFragment();
        WebBrowserInteractor interactor = new WebBrowserInteractor(context);
        WebBrowserPresenter presenter = new WebBrowserPresenter();
        WebBrowserRouter router = new WebBrowserRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;

        interactor.setupPresenter(presenter);

        return view;
    }
}

