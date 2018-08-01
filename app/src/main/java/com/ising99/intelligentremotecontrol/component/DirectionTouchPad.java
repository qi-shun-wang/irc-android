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

public class DirectionTouchPad extends ViewGroup implements GestureDetector.OnGestureListener{

    private static final float SWIPE_THRESHOLD = 30;
    private static final float SWIPE_VELOCITY_THRESHOLD = 10;
    private ImageView dot;
    private ImageView arrow;
    private int dX = 0;
    private int dY = 0;
    private int eventPointer = 0;


    private DirectionTouchPadDelegate delegate;
    private GestureDetector gesture;

    public void setDelegate(DirectionTouchPadDelegate delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
        dot.setAlpha(1f);
        dot.setX(motionEvent.getX() - dot.getWidth()/2 - dX);
        dot.setY(motionEvent.getY() - dot.getHeight()/2 - dY);
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        if (delegate == null) return false;
        delegate.didTouchOn(Action.center);
        dot.setX(motionEvent.getX() - dot.getWidth()/2 - dX);
        dot.setY(motionEvent.getY() - dot.getHeight()/2 - dY);
        dot.setAlpha(1f);
        dot.animate().alpha(0).setDuration(500).start();
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        dot.setX(dot.getX() - v);
        dot.setY(dot.getY() - v1);
        return true;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i("====>", "onLongPress: ");
    }

    @Override
    public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {

        if (delegate == null) return false;
        float diffY = motionEvent1.getY() - motionEvent.getY();
        float diffX = motionEvent1.getX() - motionEvent.getX();

        boolean result = false;

        try {
            float absDX = Math.abs(diffX);
            float absDY = Math.abs(diffY);
            dot.setAlpha(0f);
            if (absDX > absDY) {
                if (eventPointer == 2)
                {
                    delegate.didTouchOn(Action.horizontal, diffX);
                    eventPointer = 0;
                    return  true;
                }
                if (absDX > SWIPE_THRESHOLD && Math.abs(v) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffX > 0) {
                        delegate.didTouchOn(Action.right);
                    } else {
                        delegate.didTouchOn(Action.left);
                    }
                    result = true;
                }
            }
            else if (absDY > SWIPE_THRESHOLD && Math.abs(v1) > SWIPE_VELOCITY_THRESHOLD) {
                if (eventPointer == 2)
                {
                    delegate.didTouchOn(Action.vertical, diffY);
                    eventPointer = 0;
                    return  true;
                }
                if (diffY > 0) {
                    delegate.didTouchOn(Action.down);
                } else {
                    delegate.didTouchOn(Action.up);
                }
                result = true;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return result;
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
            int touch_scroll_resId = a.getResourceId(R.styleable.DirectionTouchPad_scroll_icon, R.color.white);
            dot = new ImageView(context);
            dot.setBackgroundResource(touch_dot_resId);
            dot.setScaleType(ImageView.ScaleType.FIT_CENTER);
            dot.setAlpha(0f);
            arrow = new ImageView(context);
            arrow.setBackgroundResource(touch_scroll_resId);
            arrow.setScaleType(ImageView.ScaleType.FIT_CENTER);
            arrow.setAlpha(0f);

            addView(dot);
            addView(arrow);
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

        View va = getChildAt(1);
        va.layout(i, i1,length, length);


    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_POINTER_2_DOWN) {
            eventPointer = event.getPointerCount();
            arrow.setAlpha(1f);
            Log.i("MotionEvent", "ACTION_POINTER_D: " +"pointer:"+event.getPointerCount());
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            arrow.setAlpha(0f);
        }

        performClick();
        return gesture.onTouchEvent(event);
    }
}
