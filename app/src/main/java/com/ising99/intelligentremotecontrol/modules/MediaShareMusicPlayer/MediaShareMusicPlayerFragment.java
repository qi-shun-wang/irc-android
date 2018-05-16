package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayer.MediaShareMusicPlayerContracts.Presenter;

/**
 * Created by Shun on 2018/5/15 下午 06:17:37.
 * .
 */

public class MediaShareMusicPlayerFragment extends Fragment implements MediaShareMusicPlayerContracts.View {

    private Presenter presenter;
    private ViewGroup view;

    public MediaShareMusicPlayerFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_music_player, container, false);
        view.findViewById(R.id.media_share_music_player_tool_bar_container).setOnClickListener((v) -> presenter.prepareMediaPlayerPanel());
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
    public void updateMusicInfo(String title, String subtitle, int resID) {
        ((TextView)view.findViewById(R.id.media_share_music_player_tiny_title)).setText(title);
        ((TextView)view.findViewById(R.id.media_share_music_player_tiny_subtitle)).setText(subtitle);
    }
}
