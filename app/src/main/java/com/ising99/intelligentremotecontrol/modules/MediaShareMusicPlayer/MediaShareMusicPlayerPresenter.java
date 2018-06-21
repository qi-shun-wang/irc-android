package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

import android.media.AudioManager;
import android.media.MediaPlayer;

import com.ising99.intelligentremotecontrol.App;
import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Wireframe;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/15 下午 06:17:37.
 * .
 */

public class MediaShareMusicPlayerPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private MediaPlayer player;
    private int currentVolume = 25;
    private boolean isRemoteMode = false;
    private boolean isRemotePlaying = false;
    private int remoteTimeInterval = 0;
    private Timer worker;

//    private boolean needSetAssetFirst = false;

    MediaShareMusicPlayerPresenter() {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        worker = new Timer();
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
        Music asset = interactor.getCurrentAsset();
        view.updateMusicInfo(asset.getTitle(), asset.getArtist(), R.drawable.media_share_default_album_icon);

        try {
            player.setDataSource(interactor.getCurrentAsset().getFilePath());
            player.prepare();
            prepareVolumeWith(currentVolume, player);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (interactor.isDeviceConnected()) {
//            needSetAssetFirst = true;

            isRemoteMode = true;
            interactor.setupCurrentRemoteAsset();
        }else {
            isRemoteMode = false;
        }

    }

    @Override
    public void onResume() {
        if (isRemoteMode)
        {

        }
        else
        {
            if (!player.isPlaying()) {
                player.start();
                view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
            }
        }

    }

    @Override
    public void onPause() {
        if (isRemoteMode)
        {

        }
        else
        {
            if (player.isPlaying()) {
                player.pause();
            }
        }

    }

    @Override
    public void onDestroy() {
        if (isRemoteMode) {
            worker.cancel();
            interactor.performRemoteStop();
        }

        player.stop();
        player.reset();
    }

    @Override
    public void prepareMediaPlayerPanel() {
        if (isRemoteMode){
            worker.cancel();
            player.seekTo(remoteTimeInterval);
        }

        router.presentMediaPlayerPanelWith(interactor.getAssets(), interactor.getCurrentIndex(), player, currentVolume, isRemoteMode, isRemotePlaying, !isRemoteMode);
    }

    @Override
    public void performPlayback() {
        if (isRemoteMode)
        {
            if (isRemotePlaying)
            {
                interactor.performRemotePause();
            }
            else
            {
//                if (needSetAssetFirst)
//                {
//                    needSetAssetFirst = false;
//                    interactor.setupCurrentRemoteAsset();
//                }
//                else
//                {
                interactor.performRemotePlay();
//                }

            }
        }
        else
        {
            if (player.isPlaying()) {
                player.pause();
                view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
            } else {
                player.start();
                view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
            }
        }

    }

    @Override
    public boolean performFastForward() {

        return false;
    }

    @Override
    public void performNext() {
        Music asset = interactor.playNext();
        view.updateMusicInfo(asset.getTitle(), asset.getArtist(), R.drawable.media_share_default_album_icon);
        try {
            player.stop();
            player.reset();
            player.setDataSource(asset.getFilePath());
            player.prepare();
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if(isRemoteMode)
        {
            interactor.setupCurrentRemoteAsset();
        }
        else
        {
            player.start();
        }


    }

    @Override
    public void updatePlaybackIcon(boolean isPlaying, boolean isRemoteMode, int currentIndex, int volumeScale) {
        this.isRemoteMode = isRemoteMode;

        if (isRemoteMode) {
            isRemotePlaying = isPlaying;
            remoteTimeInterval = player.getCurrentPosition();
            worker = new Timer();
            worker.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (isRemotePlaying) remoteTimeInterval+=1000;
                }
            },0,1000);
        }
        currentVolume = volumeScale;
        interactor.updateCurrentIndex(currentIndex);

        Music asset = interactor.getCurrentAsset();
        view.updateMusicInfo(asset.getTitle(), asset.getArtist(), R.drawable.media_share_default_album_icon);

        if (player.isPlaying()||isRemotePlaying) {
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        } else {
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        }
    }
    private void prepareVolumeWith(int scale,MediaPlayer player){
        float log1 = (float)(Math.log(App.MAX_VOLUME-scale)/Math.log(App.MAX_VOLUME));
        player.setVolume(1-log1,1-log1);
    }

    @Override
    public void didSetRemoteAssetSuccess() {
        interactor.performRemotePlay();
    }

    @Override
    public void didSetRemoteAssetFailure() {

    }

    @Override
    public void didPlayRemoteAssetSuccess() {
        isRemotePlaying = true;
        view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
    }

    @Override
    public void didPlayRemoteAssetFailure() {

    }

    @Override
    public void didStopRemoteAssetFailure() {

    }

    @Override
    public void didPauseRemoteAssetSuccess() {
        isRemotePlaying = false;
        view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
    }

    @Override
    public void didPauseRemoteAssetFailure() {

    }
}
