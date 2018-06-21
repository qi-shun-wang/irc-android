package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Wireframe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/5/9 下午 03:04:43.
 * .
 */

public class MediaShareVideoListPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<Video> assets;
    private String title;

    MediaShareVideoListPresenter(String title) {
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
        assets = new ArrayList<>();
        assets.addAll(interactor.getVideoAssets());
        view.setupNavigationTitle(title);
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
    public void didSelectedVideoAt(int position) {
        router.presentVideoPlayer(assets.get(position), title);
    }

    @Override
    public void performBack() {
        router.navigateBack();
    }

}
