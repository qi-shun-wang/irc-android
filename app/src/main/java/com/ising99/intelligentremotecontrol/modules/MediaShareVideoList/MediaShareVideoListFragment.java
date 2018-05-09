package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

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
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList.GridViewAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoList.MediaShareVideoListContracts.Presenter;

import java.util.List;

/**
 * Created by Shun on 2018/5/9 下午 03:04:43.
 * .
 */

public class MediaShareVideoListFragment extends Fragment implements MediaShareVideoListContracts.View ,GridViewAdapterDelegate {

    private Presenter presenter;
    private ViewGroup view;
    private ListViewAdapter adapter;
    public MediaShareVideoListFragment() {
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
        //TODO-create fragment_media_share_video_list
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_music_list, container, false);
        RecyclerView listView = view.findViewById(R.id.media_share_music_list_view);
        adapter = new ListViewAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        adapter.setupDelegate(this);
        view.findViewById(R.id.media_share_music_list_cast_btn).setOnClickListener((v)->presenter.didTapOnCast());

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
    public void reloadGridView(List<Video> assets) {
        adapter.setVideoAssets(assets);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.didSelectedVideoAt(position);
    }
}
