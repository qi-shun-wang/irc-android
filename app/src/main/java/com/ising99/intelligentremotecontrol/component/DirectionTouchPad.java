package com.ising99.intelligentremotecontrol.component;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;

/**
 * Created by shun on 2018/4/16.
 *
 */

public class DirectionTouchPad extends ViewGroup implements GestureDetector.OnGestureListener{

    private static final float SWIPE_THRESHOLD = 100;
    private static final float SWIPE_VELOCITY_THRESHOLD = 100;

    public interface DirectionTouchPadDelegate {
        void didActOn(Action action);
    }

    private DirectionTouchPadDelegate delegate;
    private GestureDetector gesture;

    public void setDelegate(DirectionTouchPadDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i("TAG", "onDown: ");
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.i("====>", "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i("====>", "onSingleTapUp: ");
        delegate.didActOn(Action.center);
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.i("====>", "onScroll: ");
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i("====>", "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.i("====>", "onFling: ");
        if (delegate == null) return false;

        boolean result = false;

        try {
            float diffY = motionEvent1.getY() - motionEvent.getY();
            float diffX = motionEvent1.getX() - motionEvent.getX();
            if (Math.abs(diffX) > Math.abs(diffY)) {
                if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(v) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        delegate.didActOn(Action.right);
                    } else {
                        delegate.didActOn(Action.left);
                    }
                    result = true;
                }
            }
            else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(v1) > SWIPE_VELOCITY_THRESHOLD) {
                if (diffY > 0) {
                    delegate.didActOn(Action.down);
                } else {
                    delegate.didActOn(Action.up);
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
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



    public DirectionTouchPad(Context context) {
        super(context);
        commonInit();
    }

    public DirectionTouchPad(Context context, AttributeSet attrs) {
        super(context, attrs);
        commonInit();
    }

    public DirectionTouchPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        commonInit();
    }

    private void commonInit(){
        gesture = new GestureDetector(getContext(), this);
    }
    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gesture.onTouchEvent(event);
    }
}
