package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import android.content.Context;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListRouter;
import com.ising99.intelligentremotecontrol.modules.BaseNavigator;

import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 04:25:19.
 * .
 */

public class MediaShareMusicGroupListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private BaseNavigator navigator;
    private DLNAMediaManagerProtocol manager;

    private MediaShareMusicGroupListRouter(Context context) {
        this.context = context;
    }

    public static MediaShareMusicGroupListFragment setupModule(Context context, DLNAMediaManagerProtocol manager, BaseNavigator navigator) {

        MediaShareMusicGroupListFragment view = new MediaShareMusicGroupListFragment();
        MediaShareMusicGroupListInteractor interactor = new MediaShareMusicGroupListInteractor(context);
        MediaShareMusicGroupListPresenter presenter = new MediaShareMusicGroupListPresenter();
        MediaShareMusicGroupListRouter router = new MediaShareMusicGroupListRouter(context);

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
    public void presentMusicAssetsWith(String title, List<Music> assets) {
        MediaShareMusicListFragment mediaSharePhotoList = MediaShareMusicListRouter.setupModule(context, title, assets, manager, navigator);
        navigator.push(mediaSharePhotoList);
    }

    @Override
    public void navigateBack() {
        navigator.pop();
    }
}

