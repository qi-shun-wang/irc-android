package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManagerProtocol;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootActivity extends Activity {

    private Fragment root;
    private DLNAMediaManagerProtocol manager;
    private RemoteControlCoAPService service;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(null);
        setContentView(R.layout.activity_root);
        service = new RemoteControlCoAPService();
        manager = new DLNAMediaManager();
        try{
            manager.setupMediaServer(getLocalIpAddress());
            manager.startServer();
        } catch (IOException e){
            e.printStackTrace();
        }
        root = RootRouter.setupModule(getApplicationContext(), service, manager);
        getFragmentManager().beginTransaction().add(R.id.fragment_root_container, root, "root").commit();

    }


    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            manager.stopServer();
            getFragmentManager().beginTransaction().remove(root).commit();
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }

    private InetAddress getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return InetAddress.getByName(String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
    }

    @Override
    public void onBackPressed() {
        if (getFragmentManager().getBackStackEntryCount() > 1) {
            getFragmentManager().popBackStack();
        } else {
            finish();
        }
    }
}
