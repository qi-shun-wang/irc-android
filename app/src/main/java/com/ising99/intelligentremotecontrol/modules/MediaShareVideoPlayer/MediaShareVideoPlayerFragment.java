package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer.MediaShareVideoPlayerContracts.Presenter;

/**
 * Created by Shun on 2018/5/10 下午 02:37:57.
 * .
 */

public class MediaShareVideoPlayerFragment extends Fragment implements MediaShareVideoPlayerContracts.View, SurfaceHolder.Callback ,SeekBar.OnSeekBarChangeListener{

    private Presenter presenter;
    private ViewGroup view;

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
        view.findViewById(R.id.navigation_back_btn).setOnClickListener((v)->presenter.performBack());
        SurfaceView mediaContainer = view.findViewById(R.id.media_share_video_player_view);
        SurfaceHolder mediaContainerHolder = mediaContainer.getHolder();
        mediaContainerHolder.addCallback(this);
        view.findViewById(R.id.media_share_video_player_cast_btn).setOnClickListener((v)->presenter.didTapOnCast());
        view.findViewById(R.id.media_share_video_player_playback_btn).setOnClickListener((v)-> presenter.performPlayBack());
        ((SeekBar)view.findViewById(R.id.seekBar)).setOnSeekBarChangeListener(this);
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
        presenter.prepareMediaPlayer(surfaceHolder);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {

    }

    @Override
    public void updatePlaybackIconWith(int resID) {
        getActivity().runOnUiThread(()-> ((ImageButton)view.findViewById(R.id.media_share_video_player_playback_btn)).setImageResource(resID));
    }

    @Override
    public void updateCastButtonWith(int resID) {
        getActivity().runOnUiThread(()-> ((ImageButton)view.findViewById(R.id.media_share_video_player_cast_btn)).setImageResource(resID));
    }

    @Override
    public void setupSeekBarMaxScale(int scale) {
        ((SeekBar)view.findViewById(R.id.seekBar)).setMax(scale);
    }

    @Override
    public void updateCurrentTimeLabel(String text) {
        getActivity().runOnUiThread(()->((TextView)view.findViewById(R.id.current_time_text)).setText(text));

    }

    @Override
    public void updateEndTimeLabel(String text) {
        getActivity().runOnUiThread(()->((TextView)view.findViewById(R.id.end_time_text)).setText(text));
    }

    @Override
    public void updateSeekBarLocation(int scale) {
        getActivity().runOnUiThread(()->((SeekBar)view.findViewById(R.id.seekBar)).setProgress(scale));
    }

    @Override
    public void showWarningBadge(String text) {
        getActivity().runOnUiThread(()->{
            //todo
            TextView warningText = view.findViewById(R.id.media_share_video_player_warning_text);
            warningText.setBackgroundColor(Color.RED);
            warningText.setText(text);
            warningText.setAlpha(1);
        });

    }

    @Override
    public void hideWarningBadge(String text) {
        getActivity().runOnUiThread(()->{
            //todo
            TextView warningText = view.findViewById(R.id.media_share_video_player_warning_text);
            warningText.setText(text);
            warningText.setBackgroundColor(Color.GREEN);
            warningText.animate().setDuration(5000).alpha(0).start();
        });
    }

    @Override
    public void setupNavigationLeftItem(String title) {
        ((Button)view.findViewById(R.id.navigation_back_btn)).setText(title);
    }

    @Override
    public void setupNavigationTitle(String title) {
        ((TextView)view.findViewById(R.id.navigation_title)).setText(title);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
        presenter.progressChangedAt(i);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        presenter.startTrackingTouch();
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        presenter.stopTrackingTouch();
    }
}
