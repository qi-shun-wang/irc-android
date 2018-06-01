package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;


/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootActivity extends Activity {

    private Fragment root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        RemoteControlCoAPService service = new RemoteControlCoAPService();
        root = RootRouter.setupModule(getApplicationContext(), service);
        getFragmentManager().beginTransaction().add(R.id.fragment_root_container,root).commit();

    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            getFragmentManager().beginTransaction().remove(root).commit();
        } catch (IllegalStateException e){
            e.printStackTrace();
        }
    }
}
