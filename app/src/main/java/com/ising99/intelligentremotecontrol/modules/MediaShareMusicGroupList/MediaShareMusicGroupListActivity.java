package com.ising99.intelligentremotecontrol.modules.MediaShareMusicGroupList;

import android.app.Activity;
import android.os.Bundle;

import com.ising99.intelligentremotecontrol.R;

public class MediaShareMusicGroupListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO-create activity_media_share_music_group_list
//        setContentView(R.layout.activity_media_share_music_group_list);
        setContentView(R.layout.activity_media_share_photo_group_list);
        MediaShareMusicGroupListFragment mediaSharePhotoGroupList = MediaShareMusicGroupListRouter.setupModule(getApplicationContext());
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_photo_group_list_container, mediaSharePhotoGroupList).commit();
        findViewById(R.id.activity_media_share_photo_group_list_back_btn).setOnClickListener((v)->finish());
    }
}
