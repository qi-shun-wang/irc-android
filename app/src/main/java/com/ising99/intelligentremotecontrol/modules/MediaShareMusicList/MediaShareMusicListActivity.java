package com.ising99.intelligentremotecontrol.modules.MediaShareMusicList;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.Music;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class MediaShareMusicListActivity extends Activity {

    private DLNAMediaManager manager = new DLNAMediaManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_music_list);

        try {
            manager.setupMediaServer(getLocalIpAddress());
        } catch (Exception e){
            e.printStackTrace();
        }
        String title = getIntent().getStringExtra("TITLE");
        List<Music> collection = getIntent().getParcelableArrayListExtra("ASSETS");
        ((TextView)findViewById(R.id.activity_media_share_music_list_title)).setText(title);

        MediaShareMusicListFragment mediaSharePhotoList = MediaShareMusicListRouter.setupModule(getApplicationContext(), collection, manager);
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_music_list_container, mediaSharePhotoList).commit();
        findViewById(R.id.activity_media_share_music_list_back_btn).setOnClickListener((v)->finish());
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            manager.startServer();
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        manager.stop((invocation, operation, defaultMsg) -> {

        });
        manager.stopServer();
    }

    private InetAddress getLocalIpAddress() throws UnknownHostException {
        WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        WifiInfo wifiInfo = wifiManager.getConnectionInfo();
        int ipAddress = wifiInfo.getIpAddress();
        return InetAddress.getByName(String.format("%d.%d.%d.%d",
                (ipAddress & 0xff), (ipAddress >> 8 & 0xff),
                (ipAddress >> 16 & 0xff), (ipAddress >> 24 & 0xff)));
    }
}
