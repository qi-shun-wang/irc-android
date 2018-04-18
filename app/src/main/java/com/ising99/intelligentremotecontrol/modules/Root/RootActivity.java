package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ising99.intelligentremotecontrol.R;


/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootActivity extends Activity {
    Fragment root;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        root = RootRouter.setupModule(getApplicationContext());
        getFragmentManager().beginTransaction().add(R.id.fragment_root_container,root).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getFragmentManager().beginTransaction().remove(root).commit();
    }
}
