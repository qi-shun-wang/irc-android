package com.ising99.intelligentremotecontrol.component;

/**
 * Created by shun on 2018/4/14.
 *
 */

public enum Action {
    up("up"),
    down("down"),
    left("left"),
    right("right"),
    center("center")
    ;

    private final String value;

    Action(String s) {
        value = s;
    }
    @Override
    public String toString() {
        return value;
    }
}
