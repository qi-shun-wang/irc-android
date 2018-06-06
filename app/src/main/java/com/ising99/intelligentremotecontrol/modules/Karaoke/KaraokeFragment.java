package com.ising99.intelligentremotecontrol.modules.Karaoke;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.model.Karaoke;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeContracts.Presenter;

import java.util.List;

/**
 * Created by Shun on 2018/4/17 下午 10:03:05.
 * .
 */

public class KaraokeFragment extends Fragment implements KaraokeContracts.View ,BaseCollectionAdapterDelegate {

    private Presenter presenter;
    private ViewGroup view;
    private RecyclerView listView;
    private ListViewAdapter adapter;

    public KaraokeFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_karaoke, container, false);
        listView = view.findViewById(R.id.karaoke_recycler_view);
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

    }

    @Override
    public void reloadListView(List<Karaoke> items) {
        adapter.setKaraokeAssets(items);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setupEmptyStatus(String message) {
        getActivity().runOnUiThread(() -> ((TextView)view.findViewById(R.id.karaoke_empty_message_text)).setText(message));
    }

    @Override
    public void showEmptyMessage() {
        getActivity().runOnUiThread(() -> view.findViewById(R.id.karaoke_empty_message_text).setAlpha(1));
    }

    @Override
    public void hideEmptyMessage() {
        getActivity().runOnUiThread(() -> view.findViewById(R.id.karaoke_empty_message_text).setAlpha(0));
    }
}
