package com.ising99.intelligentremotecontrol.core.CoapClient;

public enum SendCode {

    KEYCODE_DPAD_UP(103),
    KEYCODE_DPAD_DOWN(108),
    KEYCODE_DPAD_LEFT(105),
    KEYCODE_DPAD_RIGHT(106),
    KEYCODE_ENTER(352),
    KEYCODE_MENU(357),
    KEYCODE_BACK(158),
    KEYCODE_POWER(116),

    KEYCODE_VOLUME_UP(115),
    KEYCODE_VOLUME_DOWN(114),
    KEYCODE_VOLUME_MUTE(113),

    KEYCODE_CHANNEL_UP(402),
    KEYCODE_CHANNEL_DOWN(403),
    KEYCODE_MEDIA_PLAY_PAUSE(207),

    KEYCODE_KOD_PLUS(365),
//    KEYCODE_LANGUAGE(368),

    KEYCODE_0(11),
    KEYCODE_1(2) ,
    KEYCODE_2(3) ,
    KEYCODE_3(4),
    KEYCODE_4(5),
    KEYCODE_5(6),
    KEYCODE_6(7),
    KEYCODE_7(8),
    KEYCODE_8(9),
    KEYCODE_9(10),
    SendCode_PASS_SONG (624),
    SendCode_INSERT_SONG (625),
    SendCode_TUNING (626),
    SendCode_PLAY_CONTROL(627),
    SendCode_MAN_WOMEN (628),
    SendCode_RECORD(629),
    SendCode_APPRECIATE(630)
    ;

//    SendCode_DELETE(165),

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
