package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.component.FramesSequenceAnimation;
import com.ising99.intelligentremotecontrol.core.Device;
import com.ising99.intelligentremotecontrol.modules.BaseContracts;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;

import java.util.List;

import jp.wasabeef.blurry.Blurry;

/**
 * Created by Shun on 2018/5/23 下午 02:25:33.
 * .
 */

public class DeviceDiscoveryFragment extends Fragment implements DeviceDiscoveryContracts.View {

    private Presenter presenter;
    private ViewGroup view;
    private RecyclerView recyclerView;
    private ListViewAdapter adapter;
    private ImageView image_scan;
    private ImageView image_kod;
    private ImageView image_line;
    private TextView textKodName;
    private TextView textConnectMessage;
    private android.view.View blur;
    private FramesSequenceAnimation anim_line;
    private FramesSequenceAnimation anim_scan;

    public DeviceDiscoveryFragment() {
        // Required empty public constructor
    }

    @Override
    public void setupPresenter(BaseContracts.Presenter presenter) {
        this.presenter = (Presenter) presenter;
    }

    @Override
    public void decompose() {
        presenter = null;
        view = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = (ViewGroup) inflater.inflate(R.layout.fragment_device_discovery, container, false);
        blur = view.findViewById(R.id.blurView);
        recyclerView = view.findViewById(R.id.device_collection);
        recyclerView.setHasFixedSize(true);
        image_kod = view.findViewById(R.id.image_kod);
        image_scan = view.findViewById(R.id.scan);
        image_line = view.findViewById(R.id.image_line);
        textKodName = view.findViewById(R.id.text_kod_name);
        textConnectMessage = view.findViewById(R.id.text_connect_message);
        view.findViewById(R.id.setting_btn).setOnClickListener((v) -> presenter.openSetting());
        //todo close device discovery
        view.findViewById(R.id.cancel_btn).setOnClickListener((v) -> presenter.didTapOnClose());
        view.findViewById(R.id.search_btn).setOnClickListener((v -> presenter.searchAgain()));
        view.getViewTreeObserver().addOnWindowFocusChangeListener(b -> presenter.onWindowFocusChanged(b));
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

        presenter.onCreate();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(presenter == null) return;
        presenter.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(presenter == null) return;
        presenter.onDestroy();
    }

    @Override
    public void reloadDeviceCollection(List<Device> devices) {
        getActivity().runOnUiThread(() -> {
            adapter.setDevices(devices);
            adapter.notifyDataSetChanged();
        });
    }

    @Override
    public void performInitView() {
    }

    @Override
    public void setupListView() {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ListViewAdapter();
        adapter.setupDelegate((item,position)-> presenter.selectDeviceAt
                (
                        position,
                        item.getX(),
                        item.getY(),
                        item.getWidth(),
                        item.getHeight()
                ));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setupScanAnimation(int[] res_IDs) {
        anim_scan = new FramesSequenceAnimation(image_scan, res_IDs, 10);
    }

    @Override
    public void setupLineAnimation(int[] res_IDs) {
        anim_line = new FramesSequenceAnimation(image_line, res_IDs, 10);
    }

    @Override
    public void startScanAnimation() {
        anim_scan.start();
    }

    @Override
    public void stopScanAnimation() {
        anim_scan.stop();
        getActivity().runOnUiThread(() -> image_scan.setImageBitmap( BitmapFactory.decodeResource(getResources(), R.drawable.scan1)));
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
        getActivity().runOnUiThread(() -> {
            view.findViewById(R.id.device_collection).setVisibility(android.view.View.GONE);

            float parentCenterX = view.getX() + view.getWidth()/2;
            float parentCenterY = view.getY() + view.getHeight()/2;
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
    public void stopKODAnimation() {
        getActivity().runOnUiThread(()->view.findViewById(R.id.image_kod).clearAnimation());
    }

    @Override
    public void startLineAnimation() {
        anim_line.start();
    }

    @Override
    public void stopLineAnimation() {
        anim_line.stop();
    }

    @Override
    public void setupKodName(String text) {
        getActivity().runOnUiThread(()-> textKodName.setText(text));
    }

    @Override
    public void showConnectionSuccess() {
        getActivity().runOnUiThread(()->{
            textConnectMessage.animate().alpha(1);
            textKodName.animate().alpha(1);
            Blurry.with(getActivity()).sampling(8).onto((ViewGroup)blur);
        });
    }

    @Override
    public void showDeviceNotFound() {
        getActivity().runOnUiThread(()->{
            view.findViewById(R.id.device_not_found_container).setVisibility(android.view.View.VISIBLE);
            view.findViewById(R.id.device_not_found_container).animate().alpha(1);
            Blurry.with(getActivity()).onto((ViewGroup)blur);
        });
    }

    @Override
    public void hideDeviceNotFound() {
        getActivity().runOnUiThread(()->{
            view.findViewById(R.id.device_not_found_container).animate().alpha(0);
            view.findViewById(R.id.device_not_found_container).setVisibility(android.view.View.GONE);
            Blurry.delete((ViewGroup)blur);
        });
    }

    @Override
    public void showConnectionFailed() {
        getActivity().runOnUiThread(()->{
            view.findViewById(R.id.wifi_unavailable_container).setVisibility(android.view.View.VISIBLE);
            view.findViewById(R.id.wifi_unavailable_container).animate().alpha(1);
            Blurry.with(getActivity()).onto((ViewGroup)blur);
        });
    }

    @Override
    public void hideConnectionFailed() {
        getActivity().runOnUiThread(()->{
            view.findViewById(R.id.wifi_unavailable_container).animate().alpha(0);
            view.findViewById(R.id.wifi_unavailable_container).setVisibility(android.view.View.GONE);
            Blurry.delete((ViewGroup)blur);
        });
    }
}
