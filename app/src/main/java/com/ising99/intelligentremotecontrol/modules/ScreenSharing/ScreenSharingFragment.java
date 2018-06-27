package com.ising99.intelligentremotecontrol.modules.ScreenSharing;

import android.app.Presentation;
import android.content.Context;
import android.hardware.display.DisplayManager;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseCollectionAdapterDelegate;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.ScreenSharing.ScreenSharingContracts.Presenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shun on 2018/6/26 上午 11:34:28.
 * .
 */

public class ScreenSharingFragment extends Fragment implements ScreenSharingContracts.View ,BaseCollectionAdapterDelegate , WifiP2pManager.ActionListener{

    private Presenter presenter;
    private ViewGroup view;

    private ListViewAdapter adapter;
    private RecyclerView listView;
    private WifiP2pManager manager;
    private WifiP2pManager.Channel channel;
    private List<WifiP2pDevice> devices;

    public ScreenSharingFragment() {
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
        view = (ViewGroup) inflater.inflate(R.layout.fragment_screen_sharing, container, false);
        listView = view.findViewById(R.id.display_list);
        adapter = new ListViewAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL,false);
        listView.setHasFixedSize(true);
        listView.setLayoutManager(layoutManager);
        listView.setAdapter(adapter);
        adapter.setupDelegate(this);

        manager = (WifiP2pManager)getActivity().getSystemService(Context.WIFI_P2P_SERVICE);
        channel = manager.initialize(getActivity(), getActivity().getMainLooper(), null);
        manager.discoverPeers(channel,this);

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
        manager.cancelConnect(channel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int i) {

            }
        });
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
//        showPresentation(Arrays.asList(manager.getDisplays()).get(position));
        WifiP2pConfig config = new WifiP2pConfig();
        config.deviceAddress = devices.get(position).deviceAddress;
        manager.connect(channel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d("onSuccess", "connect: ");
            }

            @Override
            public void onFailure(int i) {

            }
        });
    }


    @Override
    public void onSuccess() {
        manager.requestPeers(channel, new WifiP2pManager.PeerListListener() {
            @Override
            public void onPeersAvailable(WifiP2pDeviceList wifiP2pDeviceList) {
                devices = new ArrayList<>(wifiP2pDeviceList.getDeviceList());
                adapter.setDisplays(devices);
                adapter.notifyDataSetChanged();
                Log.d("onPeersAvailable", "onPeersAvailable: "+wifiP2pDeviceList.getDeviceList().toString());
            }
        });
    }

    @Override
    public void onFailure(int i) {

    }

}
