package com.ising99.intelligentremotecontrol.modules.MediaShareDMRList;

import org.fourthline.cling.model.meta.Device;

/**
 * Created by Shun on 2018/4/10 上午 10:33:06.
 *
 */

public interface MediaShareDMRListFragmentDelegate {
    void didClosed();
    void didSelected(Device device);
}
