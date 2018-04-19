package com.ising99.intelligentremotecontrol.core.CoapClient;

/**
 * Created by shun on 2018/3/27.
 *
 */
@Deprecated
@SuppressWarnings("Unused enumerations")
public enum KeyCode {
    KEYCODE_APPRECIATE(630),

    KEYCODE_CHANNEL_UP(92),
    KEYCODE_CHANNEL_DOWN(93),
    KEYCODE_MEDIA_PLAY_PAUSE(85),
    KEYCODE_KOD_PLUS(365),
    KEYCODE_LANGUAGE(204);

    private int code;

    KeyCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}

