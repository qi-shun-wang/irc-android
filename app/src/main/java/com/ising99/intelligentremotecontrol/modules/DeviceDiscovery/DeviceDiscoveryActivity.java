package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.FramesSequenceAnimation;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class DeviceDiscoveryActivity extends Activity implements View {

    private GridView gridView;
    private GridViewAdapter adapter;
    private Presenter presenter;
    private ImageView image_scan;
    private ImageView image_kod;
    private ImageView image_line;
    private TextView textKodName;
    private TextView textConnectMessage;
    private android.view.View blur;

    private FramesSequenceAnimation anim_line;
    private FramesSequenceAnimation anim_scan;

    private android.view.View root;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_discovery);
        presenter = new DeviceDiscoveryPresenter(getApplicationContext(),this);
        presenter.onCreate();

    }

    @Override
    public void performInitView() {
        root = findViewById(R.id.root);
        blur = findViewById(R.id.blurView);
        gridView = findViewById(R.id.device_collection);
        image_kod = findViewById(R.id.image_kod);
        image_scan = findViewById(R.id.scan);
        image_line = findViewById(R.id.image_line);
        textKodName = findViewById(R.id.text_kod_name);
        textConnectMessage = findViewById(R.id.text_connect_message);
        findViewById(R.id.setting_btn).setOnClickListener((v)->presenter.openSetting());
        findViewById(R.id.cancel_btn).setOnClickListener((v)->finish());
        findViewById(R.id.search_btn).setOnClickListener((v -> presenter.searchAgain()));
        image_kod.setImageBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.kodpluswhite));

        image_scan.post(() -> {
            ViewGroup.LayoutParams params = image_kod.getLayoutParams();
            params.width = image_scan.getWidth();
            params.height = image_scan.getHeight();

            image_kod.setLayoutParams(params);
            image_kod.requestLayout();

            image_line.setLayoutParams(params);
            image_line.requestLayout();

            image_line.setX(image_scan.getX());
            image_line.setY( image_scan.getY()-image_scan.getHeight()/3);

        });

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
        image_scan = null;
        gridView = null;
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
            adapter.append(new ImageItem(BitmapFactory.decodeResource(getResources(), R.drawable.kodpluswhite), device.getName()));
        }

        runOnUiThread(() -> {
            gridView.setNumColumns(presenter.numberOfItem());
            adapter.notifyDataSetChanged();
        });

    }

    @Override
    public void setupGridView() {

        gridView.setNumColumns(presenter.numberOfItem());

        gridView.setOnItemClickListener((parent, view, position, id) -> presenter.selectDeviceAt
                (
                        position,
                        view.getX(),
                        view.getY(),
                        view.getWidth(),
                        view.getHeight()
                ));
    }

    @Override
    public void setupLineAnimation(int[] res_IDs) {
        anim_line = new FramesSequenceAnimation(image_line, res_IDs, 10);
    }

    @Override
    public void setupScanAnimation(int[] res_IDs) {
        anim_scan = new FramesSequenceAnimation(image_scan, res_IDs, 10);
    }

    @Override
    public void startScanAnimation() {
        anim_scan.start();
    }

    @Override
    public void stopScanAnimation() {
        anim_scan.stop();
        runOnUiThread(() -> image_scan.setImageBitmap( BitmapFactory.decodeResource(getResources(), R.drawable.scan1)));
    }

    @Override
    public void setupAdapter() {
        adapter = new GridViewAdapter(this,R.layout.grid_item_device);
        gridView.setAdapter(adapter);
    }

    @Override
    public void updateKODImagePosition(float x, float y, int width, int height) {
        image_kod.post(()->{
            image_kod.setX(x);
            image_kod.setY(y);
        });
    }

    @Override
    public void startKODAnimation() {
        anim_scan.stop();
        runOnUiThread(() -> {
            findViewById(R.id.device_collection).setVisibility(android.view.View.GONE);

            float parentCenterX = root.getX() + root.getWidth()/2;
            float parentCenterY = root.getY() + root.getHeight()/2;
            float kodCenterX =  image_kod.getX() + image_kod.getWidth()/2;
            float kodCenterY =  image_kod.getY() + image_kod.getHeight()*2/3;
            ObjectAnimator kod_fade_in = ObjectAnimator.ofFloat(image_kod, "alpha", 1f);
            ObjectAnimator line_fade_in = ObjectAnimator.ofFloat(image_line, "alpha", 1f);
            ObjectAnimator kod_move_x = ObjectAnimator.ofFloat(image_kod, "translationX", (parentCenterX - kodCenterX));
            ObjectAnimator kod_move_y = ObjectAnimator.ofFloat(image_kod, "translationY", (parentCenterY - kodCenterY));

            AnimatorSet animSet = new AnimatorSet();
            animSet.setDuration(1000);
            animSet.play(kod_move_x).with(kod_move_y).with(kod_fade_in).before(line_fade_in);
            animSet.start();
        });
    }

    @Override
    public void startLineAnimation() {
        anim_line.start();
    }

    @Override
    public void stopKODAnimation() {
        runOnUiThread(()->findViewById(R.id.image_kod).clearAnimation());

    }

    @Override
    public void stopLineAnimation() {
        anim_line.stop();
    }

    @Override
    public void setupKodName(String text) {
        runOnUiThread(()-> textKodName.setText(text));
    }

    @Override
    public void showConnectionSuccess() {
        runOnUiThread(()->{
            textConnectMessage.animate().alpha(1);
            textKodName.animate().alpha(1);
            Blurry.with(getApplicationContext()).sampling(8).onto((ViewGroup)blur);
        });
    }

    @Override
    public void showDeviceNotFound() {
        runOnUiThread(()->{
            findViewById(R.id.device_not_found_container).animate().alpha(1);
            Blurry.with(getApplicationContext()).onto((ViewGroup)blur);
        });
    }

    @Override
    public void hideDeviceNotFound() {
        runOnUiThread(()->{
            findViewById(R.id.device_not_found_container).animate().alpha(0);
            Blurry.delete((ViewGroup)blur);
        });
    }

    @Override
    public void showConnectionFailed() {
        runOnUiThread(()->{
            findViewById(R.id.wifi_unavailable_container).animate().alpha(1);
            Blurry.with(getApplicationContext()).onto((ViewGroup)blur);
        });
    }

    @Override
    public void hideConnectionFailed() {
        runOnUiThread(()->{
            findViewById(R.id.wifi_unavailable_container).animate().alpha(0);
            Blurry.delete((ViewGroup)blur);
        });
    }

    @Override
    public void finishActivity() {
        finish();
    }
}
