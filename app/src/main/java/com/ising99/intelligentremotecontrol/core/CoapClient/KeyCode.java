package com.ising99.intelligentremotecontrol.core.CoapClient;

/**
 * Created by shun on 2018/3/27.
 *
 */

public enum KeyCode {

    KEYCODE_DPAD_UP(19),
    KEYCODE_DPAD_DOWN(20),
    KEYCODE_DPAD_LEFT(21),
    KEYCODE_DPAD_RIGHT(22),
    KEYCODE_ENTER(66),
    KEYCODE_MENU(82),
    KEYCODE_BACK(4),
    KEYCODE_POWER(26);

    private int code;

    KeyCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
