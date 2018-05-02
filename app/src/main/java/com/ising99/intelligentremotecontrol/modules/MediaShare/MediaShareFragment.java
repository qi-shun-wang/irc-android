package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Presenter;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by Shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaShareFragment extends Fragment implements MediaShareContracts.View {

    private Presenter presenter;
    private ViewGroup view;
    private SectionedRecyclerViewAdapter adapter;
    private List<MediaShareItem> items = new ArrayList<>();
    public MediaShareFragment() {
        // Required empty public constructor
        items.add(new MediaShareItem("圖片",R.drawable.media_share_photo_icon));
        items.add(new MediaShareItem("音樂",R.drawable.media_share_music_icon));
        items.add(new MediaShareItem("影片",R.drawable.media_share_video_icon));
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

        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share, container, false);
        adapter = new SectionedRecyclerViewAdapter();

        adapter.addSection(new MediaShareSection("我的媒體庫",items));
        RecyclerView recyclerView =  view.findViewById(R.id.media_share_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
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
}
