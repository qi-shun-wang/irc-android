package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.ListViewAdapter;

import java.util.List;

/**
 * Created by Shun on 2018/5/8 下午 04:25:19.
 * .
 */

public class MediaShareMusicGroupListFragment extends Fragment implements MediaShareMusicGroupListContracts.View ,BaseCollectionAdapterDelegate {

    private Presenter presenter;
    private ViewGroup view;

    private ListViewAdapter adapter;

    public MediaShareMusicGroupListFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_music_group_list, container, false);
        RecyclerView listView = view.findViewById(R.id.media_share_music_group_list_view);
        listView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        listView.setLayoutManager(layoutManager);
        adapter = new ListViewAdapter();
        adapter.setupDelegate(this);
        listView.setAdapter(adapter);
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
    public void updateListView(List<String> titles) {
        adapter.setGroups(titles);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.didSelectedAt(position);
    }
}
