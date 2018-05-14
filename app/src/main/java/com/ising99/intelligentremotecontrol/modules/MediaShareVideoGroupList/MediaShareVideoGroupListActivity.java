package com.ising99.intelligentremotecontrol.modules.MediaShareVideoGroupList;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;

import com.ising99.intelligentremotecontrol.R;

public class MediaShareVideoGroupListActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_video_group_list);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
        {
            MediaShareVideoGroupListFragment mediaSharePhotoGroupList = MediaShareVideoGroupListRouter.setupModule(getApplicationContext());
            getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_video_group_list_container, mediaSharePhotoGroupList).commit();
            findViewById(R.id.activity_media_share_video_group_list_back_btn).setOnClickListener((v)->finish());
        }
        else
        {
            finish();
        }
    }
}
