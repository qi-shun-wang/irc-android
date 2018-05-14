package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;
import android.view.SurfaceHolder;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.View;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Interactor;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.InteractorOutput;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Wireframe;

import org.fourthline.cling.model.meta.Device;
import org.fourthline.cling.model.meta.RemoteDevice;

import java.io.IOException;
import java.util.Formatter;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerPresenter implements Presenter, InteractorOutput {

    private View view;
    private Interactor interactor;
    private Wireframe router;
    private boolean isRemoteMode = false;
    private boolean isRemotePlaying = false;
    private MediaPlayer player;

    private int currentSec = 0;
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
        long duration = interactor.getVideoAsset().getDuration();
        view.setupSeekBarMaxScale( (int)duration/1000 );
        view.updateEndTimeLabel("-" + transformedFrom((duration - currentSec*1000)));
        view.updateCurrentTimeLabel(transformedFrom(currentSec*1000));
        view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        if (player == null)return;
        prepareWorker();
    }

    @Override
    public void onPause() {
        worker.cancel();
        player.pause();
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
            player.seekTo(0);
            prepareWorker();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void performPlayBack() {
        if (isRemoteMode)
        {
            if(isRemotePlaying)
            {
                view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
                interactor.performPause();
            }
            else
            {
                view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
                interactor.performPlay();
            }
            isRemotePlaying = !isRemotePlaying;
        }
        else
        {
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
    }

    @Override
    public void performedSeekAt(int secScale) {
        currentSec = secScale;
        if (isRemoteMode)
        {
            interactor.performSeekAt(transformedFrom(secScale*1000));
        }
        else
        {
            player.seekTo(currentSec * 1000);
        }
    }

    @Override
    public void performingSeekAt(int secScale) {
        updateLabels(secScale);
    }

    @Override
    public void didSelected(Device device) {
        isRemoteMode = device instanceof RemoteDevice;
        if (isRemoteMode)
        {
            isRemotePlaying = true;
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
            interactor.setupCurrentDevice(device);
            interactor.performCast();
        }
    }

    @Override
    public void didTapOnCast() {
        router.presentDMRList();
    }

    private void updateLabels(int secScale){

        long duration = interactor.getVideoAsset().getDuration();
        if (secScale>=duration/1000) return;

        view.updateEndTimeLabel("-" + transformedFrom((duration - secScale*1000)));
        view.updateCurrentTimeLabel(transformedFrom(secScale*1000));
    }

    private String transformedFrom(long millis) {
        long second = (millis / 1000) % 60;
        long minute = (millis / (1000 * 60)) % 60;
        long hour = (millis / (1000 * 60 * 60)) % 24;
        StringBuilder stringBuilder = new StringBuilder();
        Formatter fmt = new Formatter(stringBuilder);
        fmt.format("%02d:%02d:%02d", hour, minute, second);
        return stringBuilder.toString();
    }

    private void prepareWorker(){
        worker = new Timer();
        worker.schedule(new TimerTask() {
            @Override
            public void run() {
                if (player.isPlaying()||isRemotePlaying) {
                    currentSec++;
                    long duration = interactor.getVideoAsset().getDuration();
                    if (currentSec>=duration/1000){
                        currentSec = 0;
                        view.updateSeekBarLocation(0);
                        view.updateEndTimeLabel("-"+transformedFrom(duration));
                        view.updateCurrentTimeLabel("00:00:00");
                        view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
                        return;
                    }
                    view.updateEndTimeLabel("-" + transformedFrom((duration - currentSec*1000)));
                    view.updateCurrentTimeLabel(transformedFrom(currentSec*1000));
                    view.updateSeekBarLocation(currentSec);
                    Log.v("==currentSec==>","currentSec:"+currentSec);
                }

            }
        },0,1000);
    }
}
