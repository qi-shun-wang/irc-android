package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Presenter;

import java.io.IOException;

/**
 * Created by Shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerFragment extends Fragment implements MediaShareVideoPlayerContracts.View, SurfaceHolder.Callback {

    private Presenter presenter;
    private ViewGroup view;
    private SurfaceView mediaContainer;
    private SurfaceHolder mediaContainerHolder;
    private MediaPlayer player;

    public MediaShareVideoPlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (Presenter) presenter;
    }

    @Override
    public void decompose() {
        presenter = null;
        view = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_video_player, container, false);
        mediaContainer = view.findViewById(R.id.media_share_video_player_view);
        mediaContainerHolder = mediaContainer.getHolder();
        mediaContainerHolder.addCallback(this);
        view.findViewById(R.id.media_share_video_player_cast_btn).setOnClickListener((v)->presenter.didTapOnCast());
        presenter.onCreate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        player = new MediaPlayer();
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setDisplay(surfaceHolder);
        try {
            player.setDataSource(presenter.getFilePath());
            player.prepare();
            player.start();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }
}
