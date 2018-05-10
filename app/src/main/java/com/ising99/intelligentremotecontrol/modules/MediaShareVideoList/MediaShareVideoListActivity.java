package com.ising99.intelligentremotecontrol.modules.MediaShareVideoList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList.Video;

import java.util.List;

public class MediaShareVideoListActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_video_list);

        String title = getIntent().getStringExtra("TITLE");
        List<Video> assets = getIntent().getParcelableArrayListExtra("ASSETS");
        ((TextView) findViewById(R.id.activity_media_share_video_list_title)).setText(title);

        MediaShareVideoListFragment mediaSharePhotoList = MediaShareVideoListRouter.setupModule(getApplicationContext(), assets, title);
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_video_list_container, mediaSharePhotoList).commit();
        findViewById(R.id.activity_media_share_video_list_back_btn).setOnClickListener((v) -> finish());
    }
}