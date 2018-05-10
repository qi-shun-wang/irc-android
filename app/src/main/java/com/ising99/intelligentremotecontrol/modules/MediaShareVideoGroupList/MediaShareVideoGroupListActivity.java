package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import android.app.Activity;
import android.os.Bundle;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListFragment;
import com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList.MediaShareMusicGroupListRouter;

public class MediaShareVideoGroupListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_video_group_list);
        MediaShareVideoGroupListFragment mediaSharePhotoGroupList = MediaShareVideoGroupListRouter.setupModule(getApplicationContext());
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_video_group_list_container, mediaSharePhotoGroupList).commit();
        findViewById(R.id.activity_media_share_video_group_list_back_btn).setOnClickListener((v)->finish());
    }
}
