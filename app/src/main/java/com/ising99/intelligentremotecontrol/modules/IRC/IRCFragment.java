package com.ising99.intelligentremotecontrol.modules.IRC;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCContracts.Presenter;

public class IRCFragment extends Fragment
        implements
        IRCContracts.View
{

    private Presenter presenter;
    private ViewGroup view;
    private View.OnClickListener showDeviceDiscoveryAction = (v) -> presenter.didTapOnDeviceDiscovery();

    @Override
    public void setupPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (IRCContracts.Presenter)presenter;
    }

    @Override
    public void decompose() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup)inflater.inflate(R.layout.fragment_irc, container, false);
        view.getViewTreeObserver().addOnWindowFocusChangeListener(b -> presenter.onWindowFocusChanged(b));
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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
    @Override
    public void setupActionBinding() {
        view.findViewById(R.id.root_wifi_container).setOnClickListener(showDeviceDiscoveryAction);
    }

    @Override
    public void updateConnectedDeviceStatus(String text) {
        getActivity().runOnUiThread(()->{
            ((AppCompatTextView)view.findViewById(R.id.root_wifi_btn)).setText(text);
        });
    }

    @Override
    public void setupConnectedDeviceImage() {
        getActivity().runOnUiThread(() -> {
            ((ImageView)view.findViewById(R.id.root_wifi_icon)).setImageResource(R.drawable.device_connect_icon);
        });
    }

    @Override
    public void setupDisconnectedDeviceImage() {
        getActivity().runOnUiThread(() ->
        {
            ((ImageView)view.findViewById(R.id.root_wifi_icon)).setImageResource(R.drawable.device_disconnect_icon);
        });
    }

    @Override
    public void showWarningBadge() {
        getActivity().runOnUiThread(() ->
        {
            view.findViewById(R.id.root_warning_text).animate().setDuration(1000).alpha(1).start();
        });
    }

    @Override
    public void hideWarningBadge() {
        getActivity().runOnUiThread(() ->
        {
            view.findViewById(R.id.root_warning_text).animate().setDuration(1000).alpha(0).start();
        });
    }


}
