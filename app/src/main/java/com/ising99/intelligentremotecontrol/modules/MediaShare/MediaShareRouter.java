package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListRouter;
import com.ising99.intelligentremotecontrol.modules.BaseNavigator;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListRouter;

/**
 * Created by shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaShareRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private BaseNavigator navigator;
    private DLNAMediaManagerProtocol manager;
    private MediaShareRouter(Context context) {
        this.context = context;
    }

    public static MediaShareFragment setupModule(Context context, BaseNavigator navigator, DLNAMediaManagerProtocol manager) {

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
        router.navigator = navigator;
        router.manager = manager;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void presentPhotoList() {
        MediaSharePhotoGroupListFragment mediaSharePhotoGroupList = MediaSharePhotoGroupListRouter.setupModule(context, manager, navigator);
        navigator.push(mediaSharePhotoGroupList);
    }

    @Override
    public void presentMusicList() {
        MediaShareMusicGroupListFragment mediaShareMusicGroupList = MediaShareMusicGroupListRouter.setupModule(context, manager, navigator);
        navigator.push(mediaShareMusicGroupList);
    }

    @Override
    public void presentVideoList() {
        MediaShareVideoGroupListFragment mediaSharePhotoGroupList = MediaShareVideoGroupListRouter.setupModule(context, manager, navigator);
        navigator.push(mediaSharePhotoGroupList);
    }

}

