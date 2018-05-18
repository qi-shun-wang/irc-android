package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;

import java.util.List;

/**
 * Created by Shun on 2018/5/16 上午 11:55:30.
 * .
 */

public class MediaShareMusicPlayerPanelFragment extends Fragment implements MediaShareMusicPlayerPanelContracts.View, BaseCollectionAdapterDelegate , MediaControlActionDelegate {

    private Presenter presenter;
    private ViewGroup view;
    private RecyclerView panel;
    private MusicPanelAdapter adapter;

    public MediaShareMusicPlayerPanelFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_music_player_panel, container, false);
        adapter = new MusicPanelAdapter();
        adapter.setDelegate(this);
        adapter.setMcaDelegate(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        panel = view.findViewById(R.id.media_share_music_player_panel_recycler_view);
        panel.setHasFixedSize(true);
        panel.setLayoutManager(layoutManager);
        panel.setAdapter(adapter);

        RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                presenter.updateScrollState(newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                presenter.updateScroll(dx,dy);
            }
        };
        panel.addOnScrollListener(listener);
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
    public void setupMusicAssets(List<Music> assets) {
        adapter.setItems(assets);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void clearPanelListener() {
        panel.clearOnScrollListeners();
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.didTapOnItemAt(position);
    }

    @Override
    public void didTapOnPlayback() {
        presenter.performPlayback();
    }

    @Override
    public void didTapOnFastBackward() {
        presenter.performFastBackward();
    }

    @Override
    public void didTapOnFastForward() {
        presenter.performFastForward();
    }

    @Override
    public void didMediaOnProgressChanged(int i, boolean b) {
        Log.v("MediaOnProgressChanged","===="+i);
        presenter.didMediaSeekAt(i);
    }

    @Override
    public void didMediaOnStartTrackingTouch() {
        Log.v("MediaStartTrackingTouch","======");
        presenter.startMediaSeeking();
    }

    @Override
    public void didMediaOnStopTrackingTouch() {
        Log.v("MediaStopTrackingTouch","======");
        presenter.stopMediaSeeking();
    }

    @Override
    public void didVolumeOnProgressChanged(int i, boolean b) {

    }

    @Override
    public void didVolumeOnStartTrackingTouch() {

    }

    @Override
    public void didVolumeOnStopTrackingTouch() {

    }

    @Override
    public void updateMediaPanel(Music asset, int currentTimeInterval, int playbackIconResID) {
        getActivity().runOnUiThread(()->{
            MusicPanelHeader header = new MusicPanelHeader(asset, currentTimeInterval, playbackIconResID);
            adapter.setHeader(header);
            adapter.notifyDataSetChanged();
        });
    }

}
