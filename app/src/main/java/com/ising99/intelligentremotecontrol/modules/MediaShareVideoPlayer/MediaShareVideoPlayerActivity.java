package com.ising99.intelligentremotecontrol.modules.MediaShareVideoPlayer;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.UPnP.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class MediaShareVideoPlayerActivity extends Activity {

    private DLNAMediaManager manager = new DLNAMediaManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_video_player);

        try {
            manager.setupMediaServer(getLocalIpAddress());
        } catch (Exception e){
            e.printStackTrace();
        }

        Video asset = getIntent().getParcelableExtra("ASSET");
        String backTitle = getIntent().getStringExtra("BACK_TITLE");

        ((TextView)findViewById(R.id.activity_media_share_video_player_title)).setText(asset.getTitle());
        ((Button)findViewById(R.id.activity_media_share_video_player_back_btn)).setText(backTitle);
        findViewById(R.id.activity_media_share_video_player_back_btn).setOnClickListener((v)->finish());

        MediaShareVideoPlayerFragment mediaSharePhotoList = MediaShareVideoPlayerRouter.setupModule(getApplicationContext(), asset, manager);
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_video_player_container, mediaSharePhotoList).commit();
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
