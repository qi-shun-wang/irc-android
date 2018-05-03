package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList;

import android.app.Activity;
import android.os.Bundle;

import com.ising99.intelligentremotecontrol.R;

public class MediaSharePhotoGroupListActivity extends Activity {

    private MediaSharePhotoGroupListFragment mediaSharePhotoGroupList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_photo_group_list);
        mediaSharePhotoGroupList = MediaSharePhotoGroupListRouter.setupModule(getApplicationContext());
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_photo_group_list_container, mediaSharePhotoGroupList).commit();
        findViewById(R.id.activity_media_share_photo_group_list_back_btn).setOnClickListener((v)->finish());
    }
}
