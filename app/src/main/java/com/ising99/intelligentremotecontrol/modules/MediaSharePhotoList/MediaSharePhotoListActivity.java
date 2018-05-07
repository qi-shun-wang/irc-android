package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.app.Activity;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.DLNA.DLNAMediaManager;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

public class MediaSharePhotoListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_photo_list);

        DLNAMediaManager manager = new DLNAMediaManager();
        try {
            manager.setupMediaServer(getLocalIpAddress());
        } catch (Exception e){
            e.printStackTrace();
        }

        String title = getIntent().getStringExtra("TITLE");
        List<Photo> collection = getIntent().getParcelableArrayListExtra("PHOTOS");
        ((TextView)findViewById(R.id.activity_media_share_photo_list_title)).setText(title);

        MediaSharePhotoListFragment mediaSharePhotoList = MediaSharePhotoListRouter.setupModule(getApplicationContext(), collection,manager);
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_photo_list_container, mediaSharePhotoList).commit();
        findViewById(R.id.activity_media_share_photo_list_back_btn).setOnClickListener((v)->finish());
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
