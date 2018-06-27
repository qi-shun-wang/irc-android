package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.BaseNavigator;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerRouter;

import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:04:43.
 * .
 */

public class MediaShareVideoListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private BaseNavigator navigator;
    private DLNAMediaManagerProtocol manager;

    private MediaShareVideoListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareVideoListFragment setupModule(Context context, DLNAMediaManagerProtocol manager, List<Video> collection, String title, BaseNavigator navigator) {

        MediaShareVideoListFragment view = new MediaShareVideoListFragment();
        MediaShareVideoListInteractor interactor = new MediaShareVideoListInteractor(context, collection);
        MediaShareVideoListPresenter presenter = new MediaShareVideoListPresenter(title);
        MediaShareVideoListRouter router = new MediaShareVideoListRouter(context);

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
    public void presentVideoPlayer(Video asset, String backTitle) {
        MediaShareVideoPlayerFragment mediaSharePhotoList = MediaShareVideoPlayerRouter.setupModule(context, backTitle, asset, manager, navigator);
        navigator.push(mediaSharePhotoList);
    }

    @Override
    public void navigateBack() {
        navigator.pop();
    }

}

