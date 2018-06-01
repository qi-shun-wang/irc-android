package com.ising99.intelligentremotecontrol.core.CoapClient;

public enum SendCode {
    KEYCODE_0(11),

    KEYCODE_1(2),

    KEYCODE_2(3),

    KEYCODE_3(4),

    KEYCODE_4(5),

    KEYCODE_5(6),

    KEYCODE_6(7),

    KEYCODE_7(8),

    KEYCODE_8(9),

    KEYCODE_9(10),

    KEYCODE_APPRECIATE(630),

    KEYCODE_BACK(158),

    KEYCODE_CHANNEL_DOWN(403),

    KEYCODE_CHANNEL_UP(402),

    KEYCODE_DEL(399),

    KEYCODE_DPAD_CENTER(352),

    KEYCODE_DPAD_DOWN(108),

    KEYCODE_DPAD_LEFT(105),

    KEYCODE_DPAD_RIGHT(106),

    KEYCODE_DPAD_UP(103),

    KEYCODE_ECHODOWN(567),

    KEYCODE_ECHOUP(569),

    KEYCODE_EPG(365),

    KEYCODE_FORWARD_DEL(398),

    KEYCODE_HOME(631),

    KEYCODE_INFO(358),

    KEYCODE_INSERT_SONG(625),

    KEYCODE_LANGUAGE_SWITCH(368),

    KEYCODE_MAN_WOMEN(628),

    KEYCODE_MEDIA_AUDIO_TRACK(574),

    KEYCODE_MEDIA_FAST_FORWARD(208),

    KEYCODE_MEDIA_PLAY_PAUSE(207),

    KEYCODE_MEDIA_RECORD(167),

    KEYCODE_MEDIA_REWIND(168),

    KEYCODE_MEDIA_STOP(128),

    KEYCODE_MENU(357),

    KEYCODE_MICDOWN(573),

    KEYCODE_MICUP(572),

    KEYCODE_MUSICDOWN(570),

    KEYCODE_MUSICUP(565),

    KEYCODE_PAGE_DOWN(109),

    KEYCODE_PAGE_UP(104),

    KEYCODE_PAIRING(632),

    KEYCODE_PASS_SONG(624),

    KEYCODE_PLAY_CONTROL(627),

    KEYCODE_POWER(116),

    KEYCODE_PROG_BLUE(401),

    KEYCODE_PROG_YELLOW(400),

    KEYCODE_RECORD(629),

    KEYCODE_RESET(568),

    KEYCODE_SUBTITLE(370),

    KEYCODE_TUNING(626),

    KEYCODE_VOCAL(566),

    KEYCODE_VOD(571),

    KEYCODE_VOLUME_DOWN(114),

    KEYCODE_VOLUME_MUTE(113),

    KEYCODE_VOLUME_UP(115),

    ;

    public static SendCode numberConvert(int number){
        switch (number){
            case 0:return SendCode.KEYCODE_0;
            case 1:return SendCode.KEYCODE_1;
            case 2:return SendCode.KEYCODE_2;
            case 3:return SendCode.KEYCODE_3;
            case 4:return SendCode.KEYCODE_4;
            case 5:return SendCode.KEYCODE_5;
            case 6:return SendCode.KEYCODE_6;
            case 7:return SendCode.KEYCODE_7;
            case 8:return SendCode.KEYCODE_8;
            case 9:return SendCode.KEYCODE_9;
            default: return SendCode.KEYCODE_0;
        }
    }

    private int code;

    SendCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}