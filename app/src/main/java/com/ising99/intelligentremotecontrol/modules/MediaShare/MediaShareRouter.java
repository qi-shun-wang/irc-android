package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListActivity;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper.Navigator;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.MediaSharePhotoGroupListRouter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListActivity;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.MediaShareVideoGroupListRouter;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Created by shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaShareRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;
    private Navigator navigator;
    private DLNAMediaManager manager = new DLNAMediaManager();
    public MediaShareRouter(Context context) {
        this.context = context;
    }

    public static MediaShareFragment setupModule(Context context, Navigator navigator) {

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
        try{
            router.manager.setupMediaServer(router.getLocalIpAddress());
            router.manager.startServer();
        } catch (IOException e){
            e.printStackTrace();
        }

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
        MediaShareVideoGroupListFragment mediaSharePhotoGroupList = MediaShareVideoGroupListRouter.setupModule(context, navigator);
        navigator.push(mediaSharePhotoGroupList);
    }

    private InetAddress getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return InetAddress.getByName(String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
    }
}

