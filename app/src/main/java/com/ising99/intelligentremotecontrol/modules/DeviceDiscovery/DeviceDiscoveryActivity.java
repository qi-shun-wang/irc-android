package com.ising99.intelligentremotecontrol.modules.DeviceDiscovery;

import android.app.Activity;
import android.os.Bundle;

import com.ising99.intelligentremotecontrol.R;
import com.ising99.intelligentremotecontrol.core.CoapClient.RemoteControlCoAPService;

public class DeviceDiscoveryActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_discovery1);

        RemoteControlCoAPService service = new RemoteControlCoAPService();
        DeviceDiscoveryFragment deviceDiscovery = DeviceDiscoveryRouter.setupModule(getApplicationContext(), service);
        getFragmentManager().beginTransaction().replace(R.id.fragment_device_discovery_container, deviceDiscovery).commit();
        
    }
}
