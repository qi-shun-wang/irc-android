package com.ising99.intelligentremotecontrol.core.CoapClient;

public enum SendCode {

    SendCode_DPAD_UP(103),
    SendCode_DPAD_DOWN(108),
    SendCode_DPAD_LEFT(105),
    SendCode_DPAD_RIGHT(106),
    SendCode_ENTER(352),
    SendCode_MENU(357),
    SendCode_BACK(158),
    SendCode_POWER(116),
    SendCode_VOLUME_UP(115),
    SendCode_VOLUME_DOWN(114),
    SendCode_VOLUME_MUTE(113),
    SendCode_CHANNEL_UP(403),
    SendCode_CHANNEL_DOWN(402),
    SendCode_MEDIA_PLAY_PAUSE(207),

    SendCode_KOD_PLUS(0),
    SendCode_LANGUAGE(0);

    private int code;

    SendCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
