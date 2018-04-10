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
 */

public class MediaShareDMRListFragment extends Fragment implements MediaShareDMRListContracts.View {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Presenter presenter;
    private MediaShareDMRListFragmentDelegate delegate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView_dmr);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mAdapter = new DMRListAdapter();
        mRecyclerView.setAdapter(mAdapter);

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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ((DMRListAdapter)mAdapter).setDevices(devices);
                mAdapter.notifyDataSetChanged();
            }
        });

    }
}
