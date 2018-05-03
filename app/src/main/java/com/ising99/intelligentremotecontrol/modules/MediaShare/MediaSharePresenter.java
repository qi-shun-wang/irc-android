package com.ising99.intelligentremotecontrol.modules.MediaShare;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Wireframe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaSharePresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private List<MediaShareItem> items = new ArrayList<>();

    MediaSharePresenter() {
        items.add(new MediaShareItem("圖片", R.drawable.media_share_photo_icon, MediaShareType.photo));
        items.add(new MediaShareItem("音樂",R.drawable.media_share_music_icon, MediaShareType.music));
        items.add(new MediaShareItem("影片",R.drawable.media_share_video_icon, MediaShareType.video));
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

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public List<MediaShareItem> prepareSectionItems() {
        return items;
    }

    @Override
    public void didSelectAt(int position) {
        switch (items.get(position).getType()){
            case photo:router.presentPhotoList();
        }
    }
}
