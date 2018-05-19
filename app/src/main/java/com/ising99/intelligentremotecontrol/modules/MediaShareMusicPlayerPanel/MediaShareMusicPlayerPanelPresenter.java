package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.media.MediaPlayer;

import com.ising99.intelligentremotecontrol.App;
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
    private int currentVolume ;
    private boolean isVolumeFirstResponse = true;

    MediaShareMusicPlayerPanelPresenter(MediaPlayer player, int volumeScale) {
        this.player = player;
        currentVolume = volumeScale;
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
        view.setupMaxVolume(App.MAX_VOLUME);
        view.setupMusicAssets(interactor.getMusicAssets());
        view.updateMediaPanel(interactor.getCurrentMusicAsset());
    }

    @Override
    public void onResume() {

        if(player.isPlaying())
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
        }
        else
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);
        }
        prepareWorker();
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
                if (hasDismissBlocker) return;

                if (record == 0 && isScrollToTop){
                    view.clearPanelListener();
                    worker.cancel();
                    router.dismissPanelWhen(player.isPlaying(), interactor.getCurrentIndex(), currentVolume);
                }
                break;
            case SCROLL_STATE_IDLE:record = 0; hasDismissBlocker = false; break;
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
            view.updateMediaPanel(interactor.getCurrentMusicAsset());
            view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void performPlayback() {
        if (player.isPlaying()) {
            player.pause();
            view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);
        } else {
            player.start();
            view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
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
            view.updateMediaPanel(asset);
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
            view.updateMediaPanel(asset);
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

    @Override
    public void startVolumeSeeking() {


    }

    @Override
    public void stopVolumeSeeking() {

    }

    @Override
    public void didVolumeSeekAt(int scale) {
        if(isVolumeFirstResponse) {
            isVolumeFirstResponse = false;
            return;
        }
        currentVolume = scale;
        prepareVolumeWith(currentVolume, player);
    }

    @Override
    public void prepareUpdateHolder() {
        view.updateCurrentVolume(currentVolume);
        view.updateCurrentMedia(player.getCurrentPosition());
        if(player.isPlaying())
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
        }
        else
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);
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
                        view.updateCurrentMedia(timeInterval);
                    }

                    if (timeInterval>=duration)view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);

                } catch (IllegalStateException e){
                    e.printStackTrace();
                }
            }
        },0,1000);
    }

    private void prepareVolumeWith(int scale,MediaPlayer player){
        float log1 = (float)(Math.log(App.MAX_VOLUME-scale)/Math.log(App.MAX_VOLUME));
        player.setVolume(1-log1,1-log1);
    }
}
