package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.content.Context;
import android.app.Fragment;

import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListContracts.View;

import java.util.List;

/**
 * Created by shun on 2018/5/3 下午 04:22:46.
 * .
 */

public class MediaSharePhotoListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    public MediaSharePhotoListRouter(Context context) {
        this.context = context;
    }

    public static MediaSharePhotoListFragment setupModule(Context context, List<Photo> collection) {

        MediaSharePhotoListFragment view = new MediaSharePhotoListFragment();
        MediaSharePhotoListInteractor interactor = new MediaSharePhotoListInteractor(context,collection);
        MediaSharePhotoListPresenter presenter = new MediaSharePhotoListPresenter();
        MediaSharePhotoListRouter router = new MediaSharePhotoListRouter(context);

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

