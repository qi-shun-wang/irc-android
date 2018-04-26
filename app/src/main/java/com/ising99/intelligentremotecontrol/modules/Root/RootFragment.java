package com.ising99.intelligentremotecontrol.modules.Root;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


/**
 * A simple {@link Fragment} subclass.
 */
public class RootFragment extends Fragment implements RootContracts.View {

    private Presenter presenter;
    private ViewGroup view;

    private View.OnClickListener showDeviceDiscoveryAction = (v) -> presenter.didTapOnDeviceDiscovery();

    public RootFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (Presenter)presenter;
    }

    @Override
    public void decompose() {
        presenter = null;
        view = null;
        showDeviceDiscoveryAction = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = (ViewGroup)inflater.inflate(R.layout.fragment_root, container, false);
        presenter.onCreate();
        return view;
    }


    @Override
    public void prepareTabBar() {
        BottomNavigationViewEx bottom = view.findViewById(R.id.bottom_navigation);
        bottom.enableAnimation(false);
        bottom.enableShiftingMode(false);
        bottom.enableItemShiftingMode(false);
        bottom.setTextSize(9);
        bottom.setOnNavigationItemSelectedListener((item)-> presenter.didSelectedTabAt(item.getOrder()));
    }

    @Override
    public void hideTabBar() {
        view.findViewById(R.id.bottom_navigation).setVisibility(View.INVISIBLE);
    }

    @Override
    public void hideNavigationBar() {
        view.findViewById(R.id.root_header_container).setVisibility(View.INVISIBLE);
    }

    @Override
    public void showNavigationBar() {
        view.findViewById(R.id.root_header_container).setVisibility(View.VISIBLE);
    }

    @Override
    public void showTabBar() {
        view.findViewById(R.id.bottom_navigation).setVisibility(View.VISIBLE);
    }

    @Override
    public void setupActionBinding() {
        view.findViewById(R.id.root_wifi_btn).setOnClickListener(showDeviceDiscoveryAction);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public void updateConnectedDeviceStatus(String text) {
        ((Button)view.findViewById(R.id.root_wifi_btn)).setText(text);
    }

    @Override
    public void updateNetworkStatus(String text) {
//        ((Button)view.findViewById(R.id.root_wifi_btn)).setText(text);
    }

    @Override
    public void setupConnectedDeviceImage() {
        ((ImageView)view.findViewById(R.id.root_wifi_icon)).setImageResource(R.drawable.device_connect_icon);
    }

    @Override
    public void setupDisconnectedDeviceImage() {
        ((ImageView)view.findViewById(R.id.root_wifi_icon)).setImageResource(R.drawable.device_disconnect_icon);
    }

    @Override
    public void showWarningBadge() {
        view.findViewById(R.id.root_warning_text).animate().setDuration(1000).alpha(1).start();
    }

    @Override
    public void hideWarningBadge() {
        view.findViewById(R.id.root_warning_text).animate().setDuration(1000).alpha(0).start();
    }

}
