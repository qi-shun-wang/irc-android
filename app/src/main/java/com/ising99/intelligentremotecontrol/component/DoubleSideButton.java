package com.ising99.intelligentremotecontrol.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ising99.intelligentremotecontrol.R;

/**
 * Created by shun on 2018/4/14.
 *
 */

public class DoubleSideButton extends ViewGroup {

    public void setDelegate(DoubleSideButtonDelegate delegate) {
        this.delegate = delegate;
    }

    public interface DoubleSideButtonDelegate {
        void dispatchAction(boolean isUpSide);
    }

    private DoubleSideButtonDelegate delegate;

    private int down_resId;
    private int up_resId;
    private int default_resId;
    private int centerY = 0;
    private ImageView image;

    public DoubleSideButton(Context context) {
        this(context, null);
    }

    public DoubleSideButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleSideButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setWillNotDraw(false);

        if (attrs!=null) {
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.DoubleSideButton);
            default_resId = a.getResourceId(R.styleable.DoubleSideButton_default_icon,R.color.white);
            down_resId = a.getResourceId(R.styleable.DoubleSideButton_down_icon,R.color.white);
            up_resId = a.getResourceId(R.styleable.DoubleSideButton_up_icon,R.color.white);
            image = new ImageView(context);
            image.setBackgroundResource(default_resId);
            addView(image,-1);
            a.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View v = getChildAt(0);
        int height = b - t;
        centerY = height /2;
        v.layout(l, t, r, b);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


        if(event.getAction() == MotionEvent.ACTION_DOWN && delegate!=null)
        {
            performClick();
            if ((int)event.getY() > centerY)
            {
                image.setBackgroundResource(down_resId);
                delegate.dispatchAction(false);
            }
            else
            {
                image.setBackgroundResource(up_resId);
                delegate.dispatchAction(true);
            }
        }
        else if(event.getAction() == MotionEvent.ACTION_UP)
        {
            image.setBackgroundResource(default_resId);
        }
        return true;
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }
}


