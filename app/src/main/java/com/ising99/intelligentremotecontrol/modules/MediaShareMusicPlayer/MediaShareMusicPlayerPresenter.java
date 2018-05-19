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

    MediaShareMusicPlayerPresenter() {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
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
    }

    @Override
    public void onResume() {
        if (!player.isPlaying()) {
            player.start();
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        }
    }

    @Override
    public void onPause() {
        if (player.isPlaying()) {
            player.pause();
        }
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.reset();
    }

    @Override
    public void prepareMediaPlayerPanel() {
        router.presentMediaPlayerPanelWith(interactor.getAssets(), interactor.getCurrentIndex(), player, currentVolume);
    }

    @Override
    public void performPlayback() {
        if (player.isPlaying()) {
            player.pause();
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        } else {
            player.start();
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
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
            player.start();
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updatePlaybackIcon(boolean isPlaying, int currentIndex, int volumeScale) {
        currentVolume = volumeScale;
        interactor.updateCurrentIndex(currentIndex);
        Music asset = interactor.getCurrentAsset();
        view.updateMusicInfo(asset.getTitle(), asset.getArtist(), R.drawable.media_share_default_album_icon);

        if (isPlaying) {
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        } else {
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        }
    }
    private void prepareVolumeWith(int scale,MediaPlayer player){
        float log1 = (float)(Math.log(App.MAX_VOLUME-scale)/Math.log(App.MAX_VOLUME));
        player.setVolume(1-log1,1-log1);
    }
}
