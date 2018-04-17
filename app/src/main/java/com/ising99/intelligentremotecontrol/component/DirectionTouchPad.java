package com.ising99.intelligentremotecontrol.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ising99.intelligentremotecontrol.R;

/**
 * Created by shun on 2018/4/16.
 *
 */

public class DirectionTouchPad extends ViewGroup implements GestureDetector.OnGestureListener{

    private static final float SWIPE_THRESHOLD = 100;
    private static final float SWIPE_VELOCITY_THRESHOLD = 100;
    private ImageView image;
    private int dX = 0;
    private int dY = 0;

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
        image.setAlpha(1f);
        image.setX(motionEvent.getX() - image.getWidth()/2 - dX);
        image.setY(motionEvent.getY() - image.getHeight()/2 - dY);
        Log.i("====>", "onShowPress: ");
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i("====>", "onSingleTapUp: ");
        if (delegate == null) return false;
        delegate.didActOn(Action.center);
        image.setAlpha(1f);
        image.setX(motionEvent.getX() - image.getWidth()/2 - dX);
        image.setY(motionEvent.getY() - image.getHeight()/2 - dY);
        image.animate().alpha(0).setDuration(500).start();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        image.setX(image.getX() - v);
        image.setY(image.getY() - v1);
        Log.i("====>", "onScroll: "+v+"-"+v1+"-"+motionEvent.getAction());
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i("====>", "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        if (delegate == null) return false;

        boolean result = false;

        try {
            float diffY = motionEvent1.getY() - motionEvent.getY();
            float diffX = motionEvent1.getX() - motionEvent.getX();
            image.setAlpha(0f);
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
        this(context, null);
    }

    public DirectionTouchPad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DirectionTouchPad(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        commonInit();
        if (attrs!=null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DirectionTouchPad);
            int touch_dot_resId = a.getResourceId(R.styleable.DirectionTouchPad_dot_icon, R.color.white);
            image = new ImageView(context);
            image.setBackgroundResource(touch_dot_resId);
            image.setScaleType(ImageView.ScaleType.FIT_CENTER);
            image.setAlpha(0f);
            addView(image,-1);
            a.recycle();
        }
    }

    private void commonInit(){
        gesture = new GestureDetector(getContext(), this);
    }


    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        View v = getChildAt(0);
        int length = Math.min(i2-i,i3-i1);
        dX = i;
        dY = i1;
        v.layout(i, i1,length/3, length/3);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        performClick();
        return gesture.onTouchEvent(event);
    }
}
