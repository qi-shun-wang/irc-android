package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShare.MediaShareContracts.Presenter;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

/**
 * Created by Shun on 2018/4/30 下午 03:49:53.
 * .
 */

public class MediaShareFragment extends Fragment implements MediaShareContracts.View, MediaShareSectionDelegate {

    private Presenter presenter;
    private ViewGroup view;
    private SectionedRecyclerViewAdapter adapter;

    public MediaShareFragment() {
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

        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share, container, false);
        adapter = new SectionedRecyclerViewAdapter();
        MediaShareSection section = new MediaShareSection("我的媒體庫",presenter.prepareSectionItems());
        section.setDelegate(this);
        adapter.addSection(section);

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

    @Override
    public void didSelectedAt(int position) {
        presenter.didSelectAt(position);
    }

    @Override
    public void showAlert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(getActivity());
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("請求檔案存取權限");
        alertBuilder.setMessage("媒體分享功能需要檔案存取的權限!");
        alertBuilder.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        200);
            }
        });

        AlertDialog alert = alertBuilder.create();
        alert.show();
    }
}
