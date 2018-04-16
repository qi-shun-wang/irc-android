package com.ising99.intelligentremotecontrol.core.DeviceDiscovery;

import java.io.Serializable;

/**
 * Created by shun on 2018/3/27.
 *
 */

public interface DeviceDiscoveryDelegate extends Serializable {
    void didReceived(String message);
}
