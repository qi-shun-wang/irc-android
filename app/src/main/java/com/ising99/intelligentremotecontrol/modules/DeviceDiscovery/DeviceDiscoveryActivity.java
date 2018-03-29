package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.GridView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryActivity extends Activity implements View {

    private GridView gridView;
    private GridViewAdapter adapter;
    private Presenter presenter;
    private Bitmap bitmap ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_discovery);

        presenter = new DeviceDiscoveryPresenter(this);
        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
        Runtime.getRuntime().gc();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.d(this.getClass().getName(), "back button pressed");
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void reloadDeviceCollection() {
        adapter.removeAll();

        for (Device device : presenter.getDevices()) {
            adapter.append(new ImageItem(bitmap, device.getName()));
        }

        runOnUiThread(() -> {

            gridView.setNumColumns(presenter.numberOfItem());
            adapter.notifyDataSetChanged();

        });

    }

    @Override
    public void setupGridView() {
        gridView = findViewById(R.id.device_collection);
        gridView.setNumColumns(presenter.numberOfItem());
        bitmap= BitmapFactory.decodeResource(getResources(), R.drawable.kodpluswhite);
        gridView.setOnItemClickListener((parent, view, position, id) -> {
            //TODO-Device Persistence

        });
    }

    @Override
    public void setupAdapter() {
        adapter = new GridViewAdapter(this,R.layout.grid_item_device);
        gridView.setAdapter(adapter);
    }
}
