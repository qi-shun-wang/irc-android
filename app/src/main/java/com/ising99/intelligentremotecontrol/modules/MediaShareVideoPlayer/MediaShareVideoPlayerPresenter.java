package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.SurfaceHolder;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Wireframe;

import org.fourthline.cling.model.meta.Device;

import java.io.IOException;
import java.util.Timer;

/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private boolean isFirstPerformedCasting = true;
    private MediaPlayer player;

    //TODO-init worker for update time travel scale
    private long currentMillis = 0;
    private Timer worker;

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
        long millis = interactor.getVideoAsset().getDuration();
        view.setupSeekBarMaxScale( (int)millis/1000 );
        view.updateEndTimeLabel("-" + transformedFrom(millis));
        view.updateCurrentTimeLabel("00:00:00");
    }

    private String transformedFrom(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;
        String time = String.format("%02d:%02d:%02d", hour, minute, second);
        return time;
    }

    @Override
    public void onPause() {
        interactor.stopCast();
    }

    @Override
    public void onDestroy() {
    }

    @Override
    public void prepareMediaPlayer(SurfaceHolder surfaceHolder) {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(surfaceHolder);
        try {
            player.setDataSource(interactor.getVideoAsset().getFilePath());
            player.prepare();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void performPlayBack() {
        if (player.isPlaying())
        {
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
            player.pause();
        }
        else
        {
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
            player.start();
        }

    }

    @Override
    public void performedSeekAt(int secScale) {
        player.seekTo(secScale*1000);
    }

    @Override
    public void performingSeekAt(int secScale) {

        view.updateEndTimeLabel("-" + transformedFrom((interactor.getVideoAsset().getDuration() - secScale*1000)));
        view.updateCurrentTimeLabel(transformedFrom(secScale*1000));
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
