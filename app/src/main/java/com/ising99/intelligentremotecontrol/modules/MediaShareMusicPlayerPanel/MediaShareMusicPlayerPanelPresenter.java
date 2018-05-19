package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.media.MediaPlayer;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Wireframe;

import java.util.Timer;
import java.util.TimerTask;

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
    private boolean hasDismissBlocker = false;
    private MediaPlayer player;
    private Timer worker;
    private boolean isSeeking = false;
    private int seekTimeInterval = 0;

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

        view.setupMusicAssets(interactor.getMusicAssets());
        if(player.isPlaying())
        {
            view.updateMediaPanel(interactor.getCurrentMusicAsset(),player.getCurrentPosition(),R.drawable.media_share_pause_icon);
        }
        else
        {
            view.updateMediaPanel(interactor.getCurrentMusicAsset(),player.getCurrentPosition(),R.drawable.media_share_play_icon);
        }
        prepareWorker();
    }

    @Override
    public void onPause() {
        worker.cancel();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void updateScrollState(int state) {
        switch (state){
            case SCROLL_STATE_DRAGGING:break;
            case SCROLL_STATE_SETTLING:
                if (hasDismissBlocker) return;

                if (record == 0 && isScrollToTop){
                    view.clearPanelListener();
                    router.dismissPanelWhen(player.isPlaying(), interactor.getCurrentIndex());
                }
                break;
            case SCROLL_STATE_IDLE:record = 0;hasDismissBlocker = false; break;
        }
    }

    @Override
    public void updateScroll(int dx, int dy) {
        isScrollToTop = dy<=0;
        record = dy;
    }

    @Override
    public void didTapOnItemAt(int position) {
        hasDismissBlocker = true;
        view.scrollToTop();

        try {
            player.stop();
            Music asset = interactor.updateCurrentIndex(position - 1);
            player.reset();
            player.setDataSource(asset.getFilePath());
            player.prepare();
            player.start();
            view.updateMediaPanel(asset,player.getCurrentPosition(),R.drawable.media_share_pause_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performPlayback() {

        if (player.isPlaying()) {
            player.pause();
            view.updateMediaPanel(interactor.getCurrentMusicAsset(),player.getCurrentPosition(),R.drawable.media_share_play_icon);
        } else {
            player.start();
            view.updateMediaPanel(interactor.getCurrentMusicAsset(),player.getCurrentPosition(),R.drawable.media_share_pause_icon);
        }
    }

    @Override
    public void performFastForward() {
        Music asset = interactor.playNext();
        try {
            player.stop();
            player.reset();
            player.setDataSource(asset.getFilePath());
            player.prepare();
            player.start();
            view.updateMediaPanel(asset,player.getCurrentPosition(),R.drawable.media_share_pause_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performFastBackward() {
        Music asset = interactor.playLast();
        try {
            player.stop();
            player.reset();
            player.setDataSource(asset.getFilePath());
            player.prepare();
            player.start();
            view.updateMediaPanel(asset,player.getCurrentPosition(),R.drawable.media_share_pause_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void startMediaSeeking() {
        isSeeking = true;
    }

    @Override
    public void stopMediaSeeking() {
        isSeeking = false;
        player.seekTo(seekTimeInterval*1000);
    }

    @Override
    public void didMediaSeekAt(int timeInterval) {
        if (isSeeking){
            seekTimeInterval = timeInterval;
        }

    }

    private void prepareWorker(){
        worker = new Timer();
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    Music asset = interactor.getCurrentMusicAsset();
                    int duration = (int)asset.getDuration();
                    int timeInterval = player.getCurrentPosition();
                    if (player.isPlaying() && !isSeeking) {
                        view.updateMediaPanel(asset, timeInterval, R.drawable.media_share_pause_icon);
                    }

                    if (timeInterval>=duration) view.updateMediaPanel(asset, 0, R.drawable.media_share_play_icon);

                } catch (IllegalStateException e){
                    e.printStackTrace();
                }
            }
        },0,1000);
    }
}
