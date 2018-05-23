package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Wireframe;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.Presenter;
import com.ising99.intelligentremotecontrol.modules.DeviceDiscovery.DeviceDiscoveryContracts.View;

/**
 * Created by shun on 2018/5/23 下午 02:25:33.
 * .
 */

public class DeviceDiscoveryRouter implements Wireframe {

    private Context context;
    private Presenter presenter;
    private View view;

    private DeviceDiscoveryRouter(Context context) {
        this.context = context;
    }

    public static DeviceDiscoveryFragment setupModule(Context context, RemoteControlCoAPService service) {

        DeviceDiscoveryFragment view = new DeviceDiscoveryFragment();
        DeviceDiscoveryInteractor interactor = new DeviceDiscoveryInteractor(context, service);
        DeviceDiscoveryPresenter presenter = new DeviceDiscoveryPresenter();
        DeviceDiscoveryRouter router = new DeviceDiscoveryRouter(context);

        view.setupPresenter(presenter);

        presenter.setupView(view);
        presenter.setupWireframe(router);
        presenter.setupInteractor(interactor);

        router.view = view;
        router.presenter = presenter;

        interactor.setupPresenter(presenter);

        return view;
    }

    @Override
    public void openWifiSetting() {
        Intent intentToWifiSetting = new Intent(Settings.ACTION_WIFI_SETTINGS);
        intentToWifiSetting.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intentToWifiSetting);
    }

    @Override
    public void dismissDeviceDiscovery() {
        presenter.decompose();
        ((DeviceDiscoveryFragment)view).getActivity().finish();
    }
}

