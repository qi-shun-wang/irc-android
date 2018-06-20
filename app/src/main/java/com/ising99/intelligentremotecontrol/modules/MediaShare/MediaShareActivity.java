package com.ising99.intelligentremotecontrol.modules.MediaShare;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;

import com.ising99.intelligentremotecontrol.R;

public class MediaShareActivity extends Activity {

    private Fragment mediaShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_share);
//        mediaShare = MediaShareRouter.setupModule(getApplicationContext());
//        getFragmentManager().beginTransaction().replace(R.id.fragment_media_share_container, mediaShare).commit();
        findViewById(R.id.activity_media_share_close_btn).setOnClickListener((v)->finish());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaShare = null;
    }
}
