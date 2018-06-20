package com.ising99.intelligentremotecontrol.modules.MediaShareNavWrapper;

import android.app.Fragment;

/**
 * Created by shun on 2018/6/20 下午 02:35:23.
 * .
 */

public interface Navigator {
    void push(Fragment fragment);
    Fragment pop();
}
