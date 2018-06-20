package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Parcelable;

import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.Navigator;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListActivity;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.MediaSharePhotoListRouter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/3 上午 10:22:42.
 * .
 */

public class MediaSharePhotoGroupListRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private Navigator navigator;
    private DLNAMediaManagerProtocol manager;

    public MediaSharePhotoGroupListRouter(Context context) {
        this.context = context;
    }

    public static MediaSharePhotoGroupListFragment setupModule(Context context, DLNAMediaManagerProtocol manager, Navigator navigator) {

        MediaSharePhotoGroupListFragment view = new MediaSharePhotoGroupListFragment();
        MediaSharePhotoGroupListInteractor interactor = new MediaSharePhotoGroupListInteractor(context);
        MediaSharePhotoGroupListPresenter presenter = new MediaSharePhotoGroupListPresenter();
        MediaSharePhotoGroupListRouter router = new MediaSharePhotoGroupListRouter(context);

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
    public void presentPhotoCollectionWith(String title, List<Photo> photos) {
        MediaSharePhotoListFragment mediaSharePhotoList = MediaSharePhotoListRouter.setupModule(context, photos, manager, navigator);
        navigator.push(mediaSharePhotoList);
    }

    @Override
    public void navigateBack() {
        navigator.pop();
    }
}

