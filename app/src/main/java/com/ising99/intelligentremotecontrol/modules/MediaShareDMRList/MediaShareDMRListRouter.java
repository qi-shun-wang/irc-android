package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.View;

/**
 * Created by shun on 2018/5/4 上午 10:51:23.
 * .
 */

public class MediaShareDMRListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaShareDMRListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareDMRListFragment setupModule(Context context, MediaShareDMRListFragmentDelegate delegate) {

        MediaShareDMRListFragment view = new MediaShareDMRListFragment();
        MediaShareDMRListInteractor interactor = new MediaShareDMRListInteractor(context);
        MediaShareDMRListPresenter presenter = new MediaShareDMRListPresenter(delegate);
        MediaShareDMRListRouter router = new MediaShareDMRListRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void dismissDMRList() {
        presenter.decompose();
    }
}

