package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareDMRList.MediaShareDMRListFragmentDelegate;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import jp.wasabeef.blurry.Blurry;


/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootActivity extends Activity implements RootContracts.View, MediaShareDMRListFragmentDelegate {

    private Presenter presenter;
    View.OnClickListener showDeviceDiscoveryAction = (v) -> presenter.didTapOnDeviceDiscovery();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        BottomNavigationViewEx bottom = findViewById(R.id.bottom_navigation);
        bottom.enableAnimation(false);
        bottom.enableShiftingMode(false);
        bottom.enableItemShiftingMode(false);
        bottom.setTextSize(6);
        bottom.setOnNavigationItemSelectedListener((item)-> {
            Log.v("ITEM",item.getTitle().toString());
            return true;
        });

        IRCFragment irc = new IRCFragment();
        getFragmentManager().beginTransaction().add(R.id.root_content_container,irc).commit();
        presenter = new RootPresenter(getApplicationContext(), this);
        presenter.onCreate();
    }

    @Override
    public void setupActionBinding() {
        findViewById(R.id.root_wifi_btn).setOnClickListener(showDeviceDiscoveryAction);

    }


    @Override
    public void showLaunchScreen() {

    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public void updateConnectedDeviceStatus(String text) {
        ((Button)findViewById(R.id.root_wifi_btn)).setText(text);
    }

    @Override
    public void updateNetworkStatus(String text) {
        ((Button)findViewById(R.id.root_wifi_btn)).setText(text);
    }

    @Override
    public void didClosed() {


    }

    @Override
    public void didSelected() {

    }
}
