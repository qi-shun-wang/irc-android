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

    private MediaPlayer player;
    private boolean isRemoteMode = false;
    private boolean isRemotePlaying = false;
    private int currentTimeInterval = 0;
    private int seekTimeInterval = 0;
    private boolean isSeeking = false;
    private Timer worker;
    private boolean shouldPlayRemoteWithSeek = true;
    private boolean isRemoteSeeking = false;

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
        view.setupSeekBarMaxScale( (int)duration/1000 - 1 );
        view.updateEndTimeLabel("-" + transformedFrom((duration - currentTimeInterval)));
        view.updateCurrentTimeLabel(transformedFrom(currentTimeInterval));
        view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        if (player == null)return;
        prepareWorker();
    }

    @Override
    public void onPause() {
        worker.cancel();
        player.pause();
        interactor.performRemoteStop();
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
                interactor.performRemotePause();
            }
            else
            {
                interactor.performRemotePlay();
            }
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
    public void startTrackingTouch() {
        if(isRemoteMode)
        {
            isRemoteSeeking = true;
        }
        else
        {
            isSeeking = true;
        }
    }

    @Override
    public void progressChangedAt(int secScale) {
        seekTimeInterval = secScale*1000;
    }

    @Override
    public void stopTrackingTouch() {
        currentTimeInterval = seekTimeInterval;
        updateLabels(currentTimeInterval);
        if(isRemoteMode)
        {
            shouldPlayRemoteWithSeek = false;
            interactor.performRemoteSeek(currentTimeInterval);
        }
        else
        {
            player.seekTo(currentTimeInterval);
        }

        isSeeking = false;
    }

    @Override
    public void didSelected(Device device) {
        isRemoteMode = device instanceof RemoteDevice;
        if (isRemoteMode)
        {
            player.pause();
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
            interactor.setupCurrentDevice(device);
            interactor.setupCurrentRemoteAsset();
        }
    }

    @Override
    public void didTapOnCast() {
        router.presentDMRList();
    }

    private void updateLabels(int currentTimeInterval){

        long duration = interactor.getVideoAsset().getDuration();
        if (currentTimeInterval>=duration) return;

        view.updateEndTimeLabel("-" + transformedFrom((duration - currentTimeInterval)));
        view.updateCurrentTimeLabel(transformedFrom(currentTimeInterval));
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
                int duration = (int) interactor.getVideoAsset().getDuration();
                if ( currentTimeInterval>=duration || currentTimeInterval >= duration-1000 )
                {
                    isRemotePlaying = false;

                    view.updateEndTimeLabel("-" + transformedFrom((duration)));
                    view.updateCurrentTimeLabel(transformedFrom(0));
                    view.updateSeekBarLocation(0);
                    view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
                    currentTimeInterval = 0;
                    Log.d("prepareWorkerEnd","===>"+currentTimeInterval/1000+"<---->"+currentTimeInterval+"<---->"+duration);
                    return;
                }

                if (isRemoteMode)
                {
                    if ((isRemotePlaying) && (!isRemoteSeeking)) {
                        interactor.fetchRemoteTimeInterval();
                        view.updateEndTimeLabel("-" + transformedFrom((duration - currentTimeInterval)));
                        view.updateCurrentTimeLabel(transformedFrom(currentTimeInterval));
                        view.updateSeekBarLocation(currentTimeInterval/1000);
                        Log.d("prepareWorker","===>"+currentTimeInterval/1000+"<---->"+currentTimeInterval+"<---->"+duration);
                    }
                }
                else
                {
                    if (player.isPlaying() && (!isSeeking)){
                        currentTimeInterval = player.getCurrentPosition();
                        view.updateEndTimeLabel("-" + transformedFrom((duration - currentTimeInterval)));
                        view.updateCurrentTimeLabel(transformedFrom(currentTimeInterval));
                        view.updateSeekBarLocation(currentTimeInterval/1000);
                        Log.d("prepareWorker","===>"+currentTimeInterval/1000+"<---->"+currentTimeInterval+"<---->"+duration);
                    }
                }
            }
        },0,500);
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
        if (shouldPlayRemoteWithSeek)
        {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    interactor.performRemoteSeek(currentTimeInterval);
                    view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
                }
            }, 1000);

        }
        else
        {
            Log.d("PlayRemoteAssetSuccess2","===>"+currentTimeInterval);
           new Timer().schedule(new TimerTask() {
               @Override
               public void run() {
                   isRemotePlaying = true;
                   view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
               }
           }, 500);

        }
    }

    @Override
    public void didPlayRemoteAssetFailure() {
        if (isRemotePlaying)
        {
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        }
        else {
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        }
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
        if (isRemotePlaying)
        {
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
        }
        else
        {
            view.updatePlaybackIconWith(R.drawable.media_share_play_icon);
        }
    }

    @Override
    public void didSeekRemoteAssetSuccess() {
        if (shouldPlayRemoteWithSeek){
            Log.d("SeekRemoteAssetSuccess","===>"+currentTimeInterval);
            view.updatePlaybackIconWith(R.drawable.media_share_pause_icon);
            shouldPlayRemoteWithSeek = false;
            isRemotePlaying = true;
        }
        isRemoteSeeking = false;
    }

    @Override
    public void didSeekRemoteAssetFailure() {
        isRemoteSeeking = false;
    }

    @Override
    public void didFetchRemoteTimeIntervalSuccess(int timeInterval) {
        if ( currentTimeInterval <= timeInterval)
            currentTimeInterval = timeInterval;
    }

    @Override
    public void didFetchRemoteTimeIntervalFailure() {

    }
}
