package com.ising99.intelligentremotecontrol.modules.KaraokeFinder;

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
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.KaraokeFinderContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.KaraokeFinder.panel.KaraokeControlPanelFragment;

import java.util.List;

/**
 * Created by Shun on 2018/6/7 下午 03:15:28.
 * .
 */

public class KaraokeFinderFragment extends Fragment implements KaraokeFinderContracts.View, BaseCollectionAdapterDelegate ,KaraokeControlPanelFragment.Delegate{

    private Presenter presenter;
    private ViewGroup view;
    private RecyclerView artistList;
    private ListViewAdapter adapter;
    private KaraokeControlPanelFragment panel;

    public KaraokeFinderFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_karaoke_finder, container, false);

        artistList = view.findViewById(R.id.karaoke_finder_list_view);
        adapter = new ListViewAdapter();
        adapter.setupDelegate(this);
        LinearLayoutManager vertical = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        artistList.setHasFixedSize(true);
        artistList.setLayoutManager(vertical);
        artistList.setAdapter(adapter);
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
        presenter.didSelectedAt(position, view.getHeight());
    }

    @Override
    public void reloadKaraokeList(List<Karaoke> karaokes) {
        adapter.setKaraokeAssets(karaokes);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showControlPanelAt(int top, int height) {
        panel = new KaraokeControlPanelFragment();
        panel.setTop(top);
        panel.setHeight(height);
        panel.setDelegate(this);
        getFragmentManager().beginTransaction().replace(R.id.karaoke_finder_control_panel_container, panel).commit();
    }

    @Override
    public void setupEmptyStatus(String message) {
        getActivity().runOnUiThread(() -> ((TextView)view.findViewById(R.id.karaoke_finder_empty_message_text)).setText(message));
    }
    @Override
    public void showEmptyMessage() {
        getActivity().runOnUiThread(() -> view.findViewById(R.id.karaoke_finder_empty_message_text).setAlpha(1));
    }

    @Override
    public void hideEmptyMessage() {
        getActivity().runOnUiThread(() -> view.findViewById(R.id.karaoke_finder_empty_message_text).setAlpha(0));
    }

    @Override
    public void didSelected() {
        getFragmentManager().beginTransaction().remove(panel).commit();
    }
}
