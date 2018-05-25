package com.ising99.intelligentremotecontrol.component;

/**
 * Created by shun on 2018/4/14.
 *
 */

public interface CircularButtonDelegate {
    void didTouchOn(Action action);
    void didTouchOnBegan(Action action);
    void didTouchOnEnd(Action action);
}
