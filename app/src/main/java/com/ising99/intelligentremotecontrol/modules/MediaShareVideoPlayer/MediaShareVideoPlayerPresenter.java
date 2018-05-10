package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Wireframe;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private boolean isFirstPerformedCasting = true;

    MediaShareVideoPlayerPresenter() {
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

    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {
        interactor.stopCast();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public String getFilePath() {
        return interactor.getVideoAsset().getFilePath();
    }

    @Override
    public void didSelected(Device device) {
        isFirstPerformedCasting = false;
        interactor.setupCurrentDevice(device);
        prepareCasting();
    }

    @Override
    public void didTapOnCast() {
        if (isFirstPerformedCasting) router.presentDMRList();
        else prepareCasting();
    }
    private void prepareCasting(){
        interactor.performCast();
    }
}
