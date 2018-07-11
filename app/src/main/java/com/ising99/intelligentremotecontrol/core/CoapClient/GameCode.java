package com.ising99.intelligentremotecontrol.core.CoapClient;

public enum GameCode {
    /*
    *
304	KEYCODE_BUTTON_A
305	KEYCODE_BUTTON_B
307	KEYCODE_BUTTON_X
308	KEYCODE_BUTTON_Y
310	KEYCODE_BUTTON_L1
311	KEYCODE_BUTTON_R1
312	KEYCODE_BUTTON_L2
313	KEYCODE_BUTTON_R2
314	KEYCODE_BUTTON_SELECT
315	KEYCODE_BUTTON_START
316	KEYCODE_BUTTON_MODE
317	KEYCODE_BUTTON_THUMBL
318	KEYCODE_BUTTON_THUMBR
    *
    *
    *
    * */

    KEYCODE_BUTTON_A(304),

    KEYCODE_BUTTON_B(305),

    KEYCODE_BUTTON_X(307),

    KEYCODE_BUTTON_Y(308),

    KEYCODE_BUTTON_L1(310),

    KEYCODE_BUTTON_R1(311),

    KEYCODE_BUTTON_L2(312),

    KEYCODE_BUTTON_R2(313),

    KEYCODE_BUTTON_SELECT(314),

    KEYCODE_BUTTON_START(315),

    KEYCODE_BUTTON_MODE(316),

    KEYCODE_BUTTON_THUMB_L(317),

    KEYCODE_BUTTON_THUMB_R(318),

    DPAD_UP(-17),

    DPAD_DOWN(17),

    DPAD_LEFT(-16),

    DPAD_RIGHT(16),

    THUMB_L_AXIS_UP(-1),
    THUMB_L_AXIS_DOWN(1),
    THUMB_L_AXIS_LEFT(-0),
    THUMB_L_AXIS_RIGHT(0),

    THUMB_R_AXIS_UP(-4),
    THUMB_R_AXIS_DOWN(4),
    THUMB_R_AXIS_LEFT(-3),
    THUMB_R_AXIS_RIGHT(3),

    ;

    private int code;

    GameCode(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
