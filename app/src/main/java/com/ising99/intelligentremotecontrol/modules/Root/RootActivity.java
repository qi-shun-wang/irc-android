package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.IRC.IRCFragment;
import com.ising99.intelligentremotecontrol.modules.Karaoke.KaraokeFragment;
import com.ising99.intelligentremotecontrol.modules.More.MoreFragment;
import com.ising99.intelligentremotecontrol.modules.Movie.MovieFragment;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.WebBrowser.WebBrowserFragment;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootActivity extends Activity implements RootContracts.View {

    private Presenter presenter;
    View.OnClickListener showDeviceDiscoveryAction = (v) -> presenter.didTapOnDeviceDiscovery();
    IRCFragment irc;
    KaraokeFragment karaoke;
    MoreFragment more;
    WebBrowserFragment web;
    MovieFragment movie;
    List<Fragment> tabs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        presenter = new RootPresenter(getApplicationContext(), this);
        presenter.onCreate();
    }

    @Override
    public void prepareTabs() {
        BottomNavigationViewEx bottom = findViewById(R.id.bottom_navigation);
        bottom.enableAnimation(false);
        bottom.enableShiftingMode(false);
        bottom.enableItemShiftingMode(false);
        bottom.setTextSize(6);
        bottom.setOnNavigationItemSelectedListener((item)-> presenter.didSelectedTabAt(item.getOrder()));

        irc = new IRCFragment();
        karaoke = new KaraokeFragment();
        more = new MoreFragment();
        web = new WebBrowserFragment();
        movie = new MovieFragment();

        tabs.add(irc);
        tabs.add(karaoke);
        tabs.add(movie);
        tabs.add(web);
        tabs.add(more);

        getFragmentManager().beginTransaction().add(R.id.root_content_container,irc).commit();
    }

    @Override
    public void setupActionBinding() {
        findViewById(R.id.root_wifi_btn).setOnClickListener(showDeviceDiscoveryAction);
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
    public void replaceCurrentTab(int order){
        getFragmentManager().beginTransaction().replace(R.id.root_content_container,tabs.get(order)).commit();
    }

}
