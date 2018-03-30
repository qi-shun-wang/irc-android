package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.KeyCode;
import com.ising99.intelligentremotecontrol.modules.Root.RootContracts.Presenter;

/**
 * Created by shun on 2018/3/27.
 *
 */

public class RootActivity extends Activity implements RootContracts.View {

    private Presenter presenter;

    View.OnClickListener showDeviceDiscoveryAction = (v) -> presenter.didTapOnDeviceDiscovery();
    View.OnClickListener tapUpAction = (v) -> presenter.didSend(KeyCode.KEYCODE_DPAD_UP);
    View.OnClickListener tapDownAction = (v) -> presenter.didSend(KeyCode.KEYCODE_DPAD_DOWN);
    View.OnClickListener tapLeftAction = (v) -> presenter.didSend(KeyCode.KEYCODE_DPAD_LEFT);
    View.OnClickListener tapRightAction = (v) -> presenter.didSend(KeyCode.KEYCODE_DPAD_RIGHT);
    View.OnClickListener tapEnterAction = (v) -> presenter.didSend(KeyCode.KEYCODE_ENTER);
    View.OnClickListener tapMenuAction = (v) -> presenter.didSend(KeyCode.KEYCODE_MENU);
    View.OnClickListener tapBackAction = (v) -> presenter.didSend(KeyCode.KEYCODE_BACK);
    View.OnClickListener tapVolumeUpAction = (v) -> presenter.didSend(KeyCode.KEYCODE_VOLUME_UP);
    View.OnClickListener tapVolumeDownAction = (v) -> presenter.didSend(KeyCode.KEYCODE_VOLUME_DOWN);
    View.OnClickListener tapVolumeMuteAction = (v) -> presenter.didSend(KeyCode.KEYCODE_VOLUME_MUTE);
    View.OnClickListener tapChannelUpAction = (v) -> presenter.didSend(KeyCode.KEYCODE_CHANNEL_UP);
    View.OnClickListener tapChannelDownAction = (v) -> presenter.didSend(KeyCode.KEYCODE_CHANNEL_DOWN);
    View.OnClickListener tapPowerAction = (v) -> presenter.didSend(KeyCode.KEYCODE_POWER);
    View.OnClickListener tapKODPlusAction = (v) -> presenter.didSend(KeyCode.KEYCODE_KOD_PLUS);
    View.OnClickListener tapLanguageAction = (v) -> presenter.didSend(KeyCode.KEYCODE_LANGUAGE);
    View.OnClickListener tapPlaybackAction = (v) -> presenter.didSend(KeyCode.KEYCODE_MEDIA_PLAY_PAUSE);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        presenter = new RootPresenter(getApplicationContext(), this);
        findViewById(R.id.button).setOnClickListener(showDeviceDiscoveryAction);
        findViewById(R.id.button_up).setOnClickListener(tapUpAction);
        findViewById(R.id.button_down).setOnClickListener(tapDownAction);
        findViewById(R.id.button_left).setOnClickListener(tapLeftAction);
        findViewById(R.id.button_right).setOnClickListener(tapRightAction);
        findViewById(R.id.button_enter).setOnClickListener(tapEnterAction);
        findViewById(R.id.button_power).setOnClickListener(tapPowerAction);
        findViewById(R.id.button_menu).setOnClickListener(tapMenuAction);
        findViewById(R.id.button_kod_plus).setOnClickListener(tapKODPlusAction);
        findViewById(R.id.button_back).setOnClickListener(tapBackAction);
        findViewById(R.id.button_volume_decrease).setOnClickListener(tapVolumeDownAction);
        findViewById(R.id.button_volume_increase).setOnClickListener(tapVolumeUpAction);
        findViewById(R.id.button_channel_increase).setOnClickListener(tapChannelUpAction);
        findViewById(R.id.button_channel_decrease).setOnClickListener(tapChannelDownAction);
        findViewById(R.id.button_mute).setOnClickListener(tapVolumeMuteAction);
        findViewById(R.id.button_channel_decrease).setOnClickListener(tapChannelDownAction);
        findViewById(R.id.button_channel_decrease).setOnClickListener(tapChannelDownAction);
        findViewById(R.id.button_language).setOnClickListener(tapLanguageAction);
        findViewById(R.id.button_playback).setOnClickListener(tapPlaybackAction);

        presenter.onCreate();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        Runtime.getRuntime().gc();
    }

    @Override
    public void updateConnectedDeviceStatus(String text) {
        ((TextView)findViewById(R.id.textView2)).setText(text);
    }

    @Override
    public void updateNetworkStatus(String text) {
        ((TextView)findViewById(R.id.textView)).setText(text);
    }

}
