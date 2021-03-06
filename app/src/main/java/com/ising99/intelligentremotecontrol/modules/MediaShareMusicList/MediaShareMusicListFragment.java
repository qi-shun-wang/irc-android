package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicList.MediaShareMusicListContracts.Presenter;

import java.util.List;

/**
 * Created by Shun on 2018/5/8 下午 05:55:17.
 * .
 */

public class MediaShareMusicListFragment extends Fragment implements MediaShareMusicListContracts.View ,BaseCollectionAdapterDelegate {

    private Presenter presenter;
    private ViewGroup view;
    private ListViewAdapter adapter;

    public MediaShareMusicListFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_music_list, container, false);
        view.findViewById(R.id.navigation_back_btn).setOnClickListener((v)->presenter.performBack());
        view.findViewById(R.id.media_share_music_player_cast_btn).setOnClickListener((view1 -> presenter.performDeviceSearch()));
        RecyclerView listView = view.findViewById(R.id.media_share_music_list_view);
        adapter = new ListViewAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        adapter.setupDelegate(this);
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
    public void onItemClick(View view, int position) {
        presenter.didSelectedMusicAt(position);
    }

    @Override
    public void reloadGridView(List<Music> assets) {
        adapter.setMusicAssets(assets);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setupNavigationTitle(String title) {
        ((TextView)view.findViewById(R.id.navigation_title)).setText(title);
    }

    @Override
    public void updateCastButtonWith(int resID) {
        getActivity().runOnUiThread(()-> ((ImageButton)view.findViewById(R.id.media_share_music_player_cast_btn)).setImageResource(resID));
    }
}
