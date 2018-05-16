package com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicPlayerPanel.MediaShareMusicPlayerPanelContracts.Presenter;
import com.karumi.headerrecyclerview.HeaderRecyclerViewAdapter;

import java.util.List;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static android.support.v7.widget.RecyclerView.SCROLL_STATE_SETTLING;

/**
 * Created by Shun on 2018/5/16 上午 11:55:30.
 * .
 */

public class MediaShareMusicPlayerPanelFragment extends Fragment implements MediaShareMusicPlayerPanelContracts.View ,GestureDetector.OnGestureListener {

    private Presenter presenter;
    private ViewGroup view;
    private RecyclerView panel;
    private HeaderRecyclerViewAdapter adapter;

    private int record = 0;

    private GestureDetector gesture;
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
        gesture = new GestureDetector(getActivity(),this);
        view.setOnTouchListener((v,e)->gesture.onTouchEvent(e));
        adapter = new MusicPanelAdapter();
        panel = view.findViewById(R.id.media_share_music_player_panel_recycler_view);
        panel.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        panel.setLayoutManager(layoutManager);
        panel.setAdapter(adapter);
        RecyclerView.OnScrollListener listener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                Log.v("onScrollStateChanged","=====>"+newState);
                switch (newState){
                    case SCROLL_STATE_DRAGGING:break;
                    case SCROLL_STATE_SETTLING:
                        if (record == 0) presenter.dismissPanel();
                        break;
                    case SCROLL_STATE_IDLE:record = 0;break;
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                Log.v("onScrolled","=====>"+dx+"-"+dy);
                record = dy;
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
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        presenter.dismissPanel();
        return true;
    }

    @Override
    public void setupMusicAssets(List<Music> assests) {
        adapter.setItems(assests);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setupCurrentMusicAsset(Music assest) {
        adapter.setHeader(new MusicPanelHeader(assest));
        adapter.notifyDataSetChanged();
    }
}
