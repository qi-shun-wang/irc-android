package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.Navigator;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListRouter;

import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:05:36.
 * .
 */

public class MediaShareVideoGroupListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private Navigator navigator;
    private DLNAMediaManagerProtocol manager;

    public MediaShareVideoGroupListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareVideoGroupListFragment setupModule(Context context, DLNAMediaManagerProtocol manager, Navigator navigator) {

        MediaShareVideoGroupListFragment view = new MediaShareVideoGroupListFragment();
        MediaShareVideoGroupListInteractor interactor = new MediaShareVideoGroupListInteractor(context);
        MediaShareVideoGroupListPresenter presenter = new MediaShareVideoGroupListPresenter();
        MediaShareVideoGroupListRouter router = new MediaShareVideoGroupListRouter(context);

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
    public void presentVideoAssetsWith(String title, List<Video> assets) {
        MediaShareVideoListFragment mediaSharePhotoList = MediaShareVideoListRouter.setupModule(context, manager, assets, title, navigator);
        navigator.push(mediaSharePhotoList);
    }

    @Override
    public void navigateBack() {
        navigator.pop();
    }
}

