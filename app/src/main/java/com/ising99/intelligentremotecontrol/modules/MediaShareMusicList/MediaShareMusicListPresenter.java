package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Wireframe;

import org.fourthline.cling.model.meta.Device;

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
    private List<Music> selectedAssets;

    private boolean isFirstPerformedCasting = true;

    MediaShareMusicListPresenter() {
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
    public void didTapOnCast() {
        if (isFirstPerformedCasting) router.presentDMRList();
        else prepareCasting();
    }

    @Override
    public void didSelected(Device device) {
        isFirstPerformedCasting = false;
        interactor.setupCurrentDevice(device);
        prepareCasting();
    }

    @Override
    public void didSelectedMusicAt(int position) {
        router.presentMediaPlayerWith(interactor.getMusicAssets(), position);
//        selectedAssets = new ArrayList<>();
//        selectedAssets.add(assets.get(position));
    }

    private void prepareCasting(){
        interactor.performCast(selectedAssets);
    }
}
