package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Wireframe;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.List;

/**
 * Created by shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<Music> assets;
    private String title;

    MediaShareMusicListPresenter(String title) {
        this.title = title;
    }

    @Override
    public void setupView(BaseContracts.View view) {
        this.view = (View) view;
    }

    @Override
    public void setupInteractor(BaseContracts.Interactor interactor) {
        this.interactor = (Interactor) interactor;
    }

    @Override
    public void setupWireframe(BaseContracts.Wireframe router) {
        this.router = (Wireframe) router;
    }

    @Override
    public void decompose() {
        interactor.decompose();
        view.decompose();
        interactor = null;
        view = null;
        router = null;
    }

    @Override
    public void onCreate() {
        view.setupNavigationTitle(title);
        if (interactor.isDeviceConnected())
        {
            view.updateCastButtonWith(R.drawable.media_share_cast_red_icon);
        }
        else
        {
            view.updateCastButtonWith(R.drawable.media_share_cast_gray_icon);
        }
        assets = interactor.getMusicAssets();
    }

    @Override
    public void onResume() {
        view.reloadGridView(assets);
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void didSelectedMusicAt(int position) {
        router.presentMediaPlayerWith(interactor.getMusicAssets(), position);
    }

    @Override
    public void performBack() {
        router.navigateBack();
    }

    @Override
    public void performDeviceSearch() {
        if (interactor.isDeviceConnected())
        {
            view.updateCastButtonWith(R.drawable.media_share_cast_gray_icon);
            interactor.clearCurrentDevice();
        }
        else
        {
            router.presentDMRList();
        }
        router.dismissCurrentPlayer();
    }

    @Override
    public void didSelected(Device device) {
        if (device instanceof RemoteDevice)
        {
            view.updateCastButtonWith(R.drawable.media_share_cast_red_icon);
            interactor.setupCurrentDevice(device);
        }
        else
        {
            view.updateCastButtonWith(R.drawable.media_share_cast_gray_icon);
        }
    }

}
