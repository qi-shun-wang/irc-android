package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Presenter;

import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.List;

/**
 * Created by Shun on 2018/5/4 上午 10:51:23.
 * .
 */

public class MediaShareDMRListFragment extends Fragment implements MediaShareDMRListContracts.View , BaseCollectionAdapterDelegate {

    private Presenter presenter;
    private ViewGroup view;
    private DMRListAdapter adapter;


    public MediaShareDMRListFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_media_share_dmr_list, container, false);
        view.findViewById(R.id.fragment_media_share_dmr_list_container).setOnClickListener((v)->presenter.performDismiss());
        view.findViewById(R.id.media_share_dmr_list_refresh_btn).setOnClickListener((v -> presenter.performRefreshTask()));
        RecyclerView recyclerView = view.findViewById(R.id.media_share_dmr_list);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new DMRListAdapter();
        adapter.setupDelegate(this);
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
    public void reloadDMRList(List<RemoteDevice> devices) {
        getActivity().runOnUiThread(() -> {
            adapter.setDevices(devices);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void updateSearchedStatus(String text) {
        getActivity().runOnUiThread(()-> ((TextView)view.findViewById(R.id.media_share_dmr_list_status_text)).setText(text));
    }

    @Override
    public void startSearchIconRotation() {
        getActivity().runOnUiThread(()-> view.findViewById(R.id.media_share_dmr_list_refresh_btn).animate().rotation(3600).setDuration(20000).setInterpolator(new LinearInterpolator()).start());
    }

    @Override
    public void stopSearchIconRotation() {
        getActivity().runOnUiThread(
                ()->
                {
                    view.findViewById(R.id.media_share_dmr_list_refresh_btn).animate().cancel();
                    view.findViewById(R.id.media_share_dmr_list_refresh_btn).setRotation(0);
                }
        );
    }


    @Override
    public void onItemClick(View view, int position) {
        presenter.prepareCastDeviceAt(position);
    }
}
