package com.ising99.intelligentremotecontrol.modules.MediaSharePhotoList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.modules.MediaSharePhotoGroupList.Photo;

import java.util.List;

public class MediaSharePhotoListActivity extends Activity {

    private MediaSharePhotoListFragment mediaSharePhotoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share_photo_list);

        String title = getIntent().getStringExtra("TITLE");
        List<Photo> collection = getIntent().getParcelableArrayListExtra("PHOTOS");
        ((TextView)findViewById(R.id.activity_media_share_photo_list_title)).setText(title);

        mediaSharePhotoList = MediaSharePhotoListRouter.setupModule(getApplicationContext(),collection);
        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_photo_list_container, mediaSharePhotoList).commit();
        findViewById(R.id.activity_media_share_photo_list_back_btn).setOnClickListener((v)->finish());
    }

}
