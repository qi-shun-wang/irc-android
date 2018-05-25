package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.media.MediaPlayer;
import android.util.Log;

import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Wireframe;

import org.fourthline.cling.model.meta.Device;

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
    private boolean isFirstPerformedCasting = true;
    private boolean isRemoteCasting = false;
    private boolean isRemotePlaying = false;
    private boolean isRemoteSeeking = false;
    private int remoteTimeInterval = 0;
    private boolean shouldPlayRemoteWithSeek = true;

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
        if (isRemoteCasting){

        }
        else
        {
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

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroy() {
        worker.cancel();
    }

    @Override
    public void updateScrollState(int state) {
        switch (state){
            case SCROLL_STATE_DRAGGING:break;
            case SCROLL_STATE_SETTLING:
                if (hasDismissBlocker) return;

                if (record == 0 && isScrollToTop)
                {
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
        Music asset = interactor.updateCurrentIndex(position - 1);
        view.updateMediaPanel(interactor.getCurrentMusicAsset());
        view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
        player.stop();

        if (isRemoteCasting)
        {
            remoteTimeInterval = 0;
            interactor.setupCurrentRemoteAsset();
        }
        else
        {
            try {
                player.reset();
                player.setDataSource(asset.getFilePath());
                player.prepare();
                player.start();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void performCast() {
        router.presentDMRList();
    }

    @Override
    public void didSelected(Device device) {
        isFirstPerformedCasting = false;
        isRemoteCasting = true;
        player.pause();
        remoteTimeInterval = player.getCurrentPosition();
        Log.d("getCurrentPosition","===>"+remoteTimeInterval);
        interactor.setupCurrentDevice(device);
        interactor.setupCurrentRemoteAsset();
    }

    @Override
    public void performPlayback() {
        if (isRemoteCasting)
        {
            //todo lock playback button
            if (isRemotePlaying)
            {
                interactor.performRemotePause();
            }
            else {
                interactor.performRemotePlay();
            }
        }
        else
        {
            if (player.isPlaying()) {
                player.pause();
                view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);
            } else {
                player.start();
                view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
            }
        }

    }

    @Override
    public void performFastForward() {
        Music asset = interactor.playNext();
        isRemotePlaying = false;
        view.updateCurrentMedia(0);
        view.updateMediaPanel(asset);
        if (isRemoteCasting)
        {

            interactor.setupCurrentRemoteAsset();
        }
        else
        {
            try {
                player.stop();
                player.reset();
                player.setDataSource(asset.getFilePath());
                player.prepare();
                player.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void performFastBackward() {
        Music asset = interactor.playLast();
        view.updateCurrentMedia(0);
        view.updateMediaPanel(asset);
        isRemotePlaying = false;
        if (isRemoteCasting)
        {
            interactor.setupCurrentRemoteAsset();
        }
        else
        {
            try {
                player.stop();
                player.reset();
                player.setDataSource(asset.getFilePath());
                player.prepare();
                player.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void startMediaSeeking() {
        if (isRemoteCasting)
        {
            isRemoteSeeking = true;
        }
        else {
            isSeeking = true;
        }
    }

    @Override
    public void stopMediaSeeking() {

        if (isRemoteCasting)
        {
            remoteTimeInterval = seekTimeInterval*1000;
            Log.d("stopMediaSeeking","===>"+remoteTimeInterval);
            interactor.performRemoteSeek(remoteTimeInterval);
        }
        else
        {
            isSeeking = false;
            player.seekTo(seekTimeInterval*1000);
        }

    }

    @Override
    public void didMediaSeekAt(int timeInterval) {
        if (isSeeking||isRemoteSeeking){
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
        if(player.isPlaying()||isRemotePlaying)
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
                    int timeInterval ;
                    if (isRemoteCasting)
                    {
                        timeInterval = remoteTimeInterval;
                        if ((isRemotePlaying) && (!isRemoteSeeking)) {
                            view.updateCurrentMedia(timeInterval);
                            remoteTimeInterval+=1000;
                        }
                        Log.d("prepareWorker","===>"+remoteTimeInterval);
                    }
                    else
                    {
                        timeInterval = player.getCurrentPosition();
                        if ((player.isPlaying()) && (!isSeeking)) {
                            view.updateCurrentMedia(timeInterval);
                        }
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

    @Override
    public void didSetRemoteAssetSuccess() {
        //TODO show set Success warning badge
        Log.d("SetRemoteAssetSuccess","===>"+remoteTimeInterval);

        interactor.performRemotePlay();


    }

    @Override
    public void didSetRemoteAssetFailure() {
        //TODO show set failure warning badge

    }

    @Override
    public void didPlayRemoteAssetSuccess() {
        //TODO show Play Success warning badge

        if (shouldPlayRemoteWithSeek)
        {
            try {
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            interactor.performRemoteSeek(remoteTimeInterval);
        }
        else
        {
            Log.d("PlayRemoteAssetSuccess2","===>"+remoteTimeInterval);
            remoteTimeInterval = 0;
            isRemotePlaying = true;

        }
        view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
    }

    @Override
    public void didPlayRemoteAssetFailure() {
        //TODO show Play failure warning badge
        if (isRemotePlaying)
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
        }
        else
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);
        }
    }

    @Override
    public void didStopRemoteAssetFailure() {
        //TODO show Stop failure warning badge
    }

    @Override
    public void didPauseRemoteAssetSuccess() {
        //TODO show Pause Success warning badge
        isRemotePlaying = false;
        view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);
    }

    @Override
    public void didPauseRemoteAssetFailure() {
        //TODO show Pause failure warning badge
        if (isRemotePlaying)
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
        }
        else
        {
            view.updateCurrentPlaybackIcon(R.drawable.media_share_play_icon);
        }
    }

    @Override
    public void didSeekRemoteAssetSuccess() {
        //TODO show warning badge

        if (shouldPlayRemoteWithSeek){
            Log.d("SeekRemoteAssetSuccess","===>"+remoteTimeInterval);
            view.updateCurrentPlaybackIcon(R.drawable.media_share_pause_icon);
            shouldPlayRemoteWithSeek = false;
            isRemotePlaying = true;
        }
        isRemoteSeeking = false;

    }

    @Override
    public void didSeekRemoteAssetFailure() {
        //TODO show warning badge
    }
}
