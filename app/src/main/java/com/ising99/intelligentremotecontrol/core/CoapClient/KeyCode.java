package com.ising99.intelligentremotecontrol.core.CoapClient;

/**
 * Created by shun on 2018/3/27.
 *
 */
@Deprecated
@SuppressWarnings("Unused enumerations")
public enum KeyCode {

    KEYCODE_DPAD_UP(19),
    KEYCODE_DPAD_DOWN(20),
    KEYCODE_DPAD_LEFT(21),
    KEYCODE_DPAD_RIGHT(22),
    KEYCODE_ENTER(66),
    KEYCODE_MENU(82),
    KEYCODE_BACK(4),
    KEYCODE_POWER(26),

    KEYCODE_VOLUME_UP(24),
    KEYCODE_VOLUME_DOWN(25),
    KEYCODE_VOLUME_MUTE(164),

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

