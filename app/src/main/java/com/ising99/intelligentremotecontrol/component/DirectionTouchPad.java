package com.ising99.intelligentremotecontrol.component;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by shun on 2018/4/16.
 *
 */

public class DirectionTouchPad extends ViewGroup {

    private DirectionTouchPadDelegate delegate;

    public void setDelegate(DirectionTouchPadDelegate delegate) {
        this.delegate = delegate;
    }

    public enum Action {
        up("up"),
        down("down"),
        left("left"),
        right("right"),
        center("center");

        private final String value;

        Action(String s) {
            value = s;
        }
        @Override
        public String toString() {
            return value;
        }
    }

    public interface DirectionTouchPadDelegate {
        void didActOn(Action action);
    }

    public DirectionTouchPad(Context context) {
        super(context);
    }

    public DirectionTouchPad(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DirectionTouchPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return super.onTouchEvent(event);
    }
}
