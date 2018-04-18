package com.ising99.intelligentremotecontrol.modules.WebBookmark;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.WebBookmark.WebBookmarkContracts.View;

/**
 * Created by shun on 2018/4/17 下午 10:59:43.
 * .
 */

public class WebBookmarkRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public WebBookmarkRouter(Context context) {
        this.context = context;
    }

    public static WebBookmarkFragment setupModule(Context context) {

        WebBookmarkFragment view = new WebBookmarkFragment();
        WebBookmarkInteractor interactor = new WebBookmarkInteractor(context);
        WebBookmarkPresenter presenter = new WebBookmarkPresenter();
        WebBookmarkRouter router = new WebBookmarkRouter(context);

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

