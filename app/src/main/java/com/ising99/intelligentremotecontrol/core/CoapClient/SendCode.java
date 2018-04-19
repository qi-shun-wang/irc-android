package com.ising99.intelligentremotecontrol.core.CoapClient;

public enum SendCode {
    /*
key 116     POWER
key 113		VOLUME_MUTE
key	398		FORWARD_DEL
key	399		DEL
key	400		LANGUAGE_SWITCH
key	401		PROG_BLUE
key 207     MEDIA_PLAY_PAUSE
key 128     MAN_WOMEN
key 565		MUSICUP
key 566		PLAY_CONTROL
key	167		RECORD
key 567		ECHODOWN
key 568		RESET
key 569		ECHOUP
key 168     MEDIA_REWIND
key 208     MEDIA_FAST_FORWARD
key 570		MUSICDOWN
key	574 	TUNING
key 357     MENU
key 571		APPRECIATE
key 103     DPAD_UP
key 105     DPAD_LEFT
key 352     DPAD_CENTER
key 106     DPAD_RIGHT
key 108     DPAD_DOWN
key 158     BACK
key	365 	HOME
key 115     VOLUME_UP
key 114     VOLUME_DOWN
key 572		MICUP
key 573		MICDOWN
key 402		PAGE_UP
key 403		PAGE_DOWN
key   2		1
key   3		2
key   4		3
key   5		4
key   6		5
key   7		6
key   8		7
key   9		8
key  10		9
key  11		0
key 358   PASS_SONG
key 370   INSERT_SONG

    * */
    KEYCODE_POWER(116),

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

    KEYCODE_DEL(399),

    KEYCODE_PASS_SONG(358),
    KEYCODE_INSERT_SONG(370),
    KEYCODE_TUNING(574),
    KEYCODE_PLAY_CONTROL(566),
    KEYCODE_MAN_WOMEN(128),
    KEYCODE_RECORD(167),
    KEYCODE_APPRECIATE(571),

    KEYCODE_DPAD_UP(103),
    KEYCODE_DPAD_DOWN(108),
    KEYCODE_DPAD_LEFT(105),
    KEYCODE_DPAD_RIGHT(106),
    KEYCODE_ENTER(352),

    KEYCODE_MENU(357),
    KEYCODE_MEDIA_PLAY_PAUSE(207),
    KEYCODE_BACK(158),

    KEYCODE_VOLUME_UP(115),
    KEYCODE_VOLUME_DOWN(114),

    KEYCODE_KOD_PLUS(365),

    KEYCODE_PAGE_DOWN(403),
    KEYCODE_PAGE_UP(402),

    KEYCODE_VOLUME_MUTE(113),
    KEYCODE_LANGUAGE(400),

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
