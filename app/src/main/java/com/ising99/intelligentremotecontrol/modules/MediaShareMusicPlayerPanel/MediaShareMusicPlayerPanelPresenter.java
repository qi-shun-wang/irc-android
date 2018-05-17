package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.media.MediaPlayer;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Wireframe;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by shun on 2018/5/16 上午 11:55:30.
 * .
 */

public class MediaShareMusicPlayerPanelPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private int record = 0;
    private boolean isScrollToTop = true;
    private MediaPlayer player;

    MediaShareMusicPlayerPanelPresenter(MediaPlayer player) {
        this.player = player;
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
        view.setupCurrentMusicAsset(interactor.getCurrentMusicAsset());
        view.setupMusicAssets(interactor.getMusicAssets());
        if(player.isPlaying()){
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        }else{
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        }
    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void updateScrollState(int state) {
        switch (state){
            case SCROLL_STATE_DRAGGING:break;
            case SCROLL_STATE_SETTLING:
                if (record == 0 && isScrollToTop){
                    view.clearPanelListener();
                    router.dismissPanelWhen(player.isPlaying());
                }
                break;
            case SCROLL_STATE_IDLE:record = 0;break;
        }
    }

    @Override
    public void updateScroll(int dx, int dy) {
        isScrollToTop = dy<=0;
        record = dy;
    }

    @Override
    public void didTapOnItemAt(int position) {

    }

    @Override
    public void performPlayback() {
        if(player.isPlaying()){
            player.pause();
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        }else{
            player.start();
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        }
    }
}
