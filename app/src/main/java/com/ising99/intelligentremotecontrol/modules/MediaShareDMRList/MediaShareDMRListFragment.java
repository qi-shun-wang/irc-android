package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListContracts.Presenter;

import org.fourthline.cling.model.meta.RemoteDevice;

import java.util.List;

/**
 * Created by shun on 2018/4/10.
 * .
 */

public class MediaShareDMRListFragment extends Fragment implements MediaShareDMRListContracts.View ,DMRListAdapterDelegate {

    private DMRListAdapter adapter;
    private Presenter presenter;
    private MediaShareDMRListFragmentDelegate delegate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_media_share_dmr_list, container, false);
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter = new MediaShareDMRListPresenter(getActivity().getApplicationContext(),this);
        view.findViewById(R.id.fragment_media_share_dmr_list_container).setOnClickListener((v)->delegate.didClosed());
        RecyclerView recyclerView = view.findViewById(R.id.recyclerView_dmr);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DMRListAdapter();
        adapter.setupDelegate(this);
        recyclerView.setAdapter(adapter);
        presenter.onCreate();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        //TODO Setup OnFragmentInteractionListener

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
        delegate = null;
        Runtime.getRuntime().gc();
    }

    public void setDelegate(MediaShareDMRListFragmentDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public void reloadDMRList(List<RemoteDevice> devices) {
        getActivity().runOnUiThread(() -> {
            adapter.setDevices(devices);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void onItemClick(View view, int position) {
        presenter.prepareCastDeviceAt(position);
    }
}
