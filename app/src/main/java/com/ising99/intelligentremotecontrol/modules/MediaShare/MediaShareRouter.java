package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.View;

/**
 * Created by shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaShareRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaShareRouter(Context context) {
        this.context = context;
    }

    public static MediaShareFragment setupModule(Context context) {

        MediaShareFragment view = new MediaShareFragment();
        MediaShareInteractor interactor = new MediaShareInteractor(context);
        MediaSharePresenter presenter = new MediaSharePresenter();
        MediaShareRouter router = new MediaShareRouter(context);

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
