package com.ising99.intelligentremotecontrol.modules.Root;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);
        presenter = new RootPresenter(this);
        findViewById(R.id.button).setOnClickListener(showDeviceDiscoveryAction);
        findViewById(R.id.button2).setOnClickListener(tapUpAction);
        findViewById(R.id.button3).setOnClickListener(tapDownAction);
        findViewById(R.id.button4).setOnClickListener(tapLeftAction);
        findViewById(R.id.button5).setOnClickListener(tapRightAction);
        findViewById(R.id.button6).setOnClickListener(tapEnterAction);
        presenter.onCreate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        Runtime.getRuntime().gc();
    }
}
