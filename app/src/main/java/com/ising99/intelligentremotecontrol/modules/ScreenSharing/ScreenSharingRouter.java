package com.ising99.intelligentremotecontrol.modules.ScreenSharing;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.View;

/**
 * Created by shun on 2018/6/26 上午 11:34:28.
 * .
 */

public class ScreenSharingRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    private ScreenSharingRouter(Context context) {
        this.context = context;
    }

    public static ScreenSharingFragment setupModule(Context context) {

        ScreenSharingFragment view = new ScreenSharingFragment();
        ScreenSharingInteractor interactor = new ScreenSharingInteractor(context);
        ScreenSharingPresenter presenter = new ScreenSharingPresenter();
        ScreenSharingRouter router = new ScreenSharingRouter(context);

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

