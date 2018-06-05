package com.ising99.intelligentremotecontrol.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ising99.intelligentremotecontrol.R;

/**
 * Created by shun on 2018/4/14.
 *
 */

public class CircularButton extends ViewGroup {

    private CircularButtonDelegate delegate;
    private int LONG_PRESS_DELAY = 500;
    private Region downRegion;
    private Region upRegion;
    private Region rightRegion;
    private Region leftRegion;
    private Region centerRegion;
    private Region outerRegion;

    private int dpad_ok_resId;
    private int dpad_left_resId;
    private int dpad_right_resId;
    private int dpad_down_resId;
    private int dpad_up_resId;
    private int dpad_default_resId;

    private int length = 0;
    private int centerX = 0;
    private int centerY = 0;
    private ImageView image;

    private Action currentAction;
    private boolean firstResponseLongPressed = false;

    private final Handler handler = new Handler();

    private Runnable onLongPressed = () -> {
        Log.i("ACTION_DOWN", "Long press action down!");

        firstResponseLongPressed = true;
        if (currentAction!=null) delegate.didTouchOnBegan(currentAction);
    };

    private Runnable onTap = () -> handler.postDelayed(onLongPressed, LONG_PRESS_DELAY - ViewConfiguration.getTapTimeout());

    public CircularButton(Context context) {
        this(context, null);
    }

    public CircularButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircularButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        if (attrs!=null) {
            TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.CircularButton);
            dpad_default_resId = a.getResourceId(R.styleable.CircularButton_dpad_default_icon,R.color.white);
            dpad_ok_resId = a.getResourceId(R.styleable.CircularButton_dpad_ok_icon,R.color.white);
            dpad_down_resId = a.getResourceId(R.styleable.CircularButton_dpad_down_icon,R.color.white);
            dpad_up_resId = a.getResourceId(R.styleable.CircularButton_dpad_up_icon,R.color.white);
            dpad_left_resId = a.getResourceId(R.styleable.CircularButton_dpad_left_icon,R.color.white);
            dpad_right_resId = a.getResourceId(R.styleable.CircularButton_dpad_right_icon,R.color.white);

            image = new ImageView(context);
            image.setBackgroundResource(dpad_default_resId);

            addView(image,0);
            a.recycle();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        View v = getChildAt(0);
        int width = r - l;
        int height = b - t;
        centerX = width /2;
        centerY = height /2;
        length = Math.min(width, height);
        int newL = centerX - length/2;
        int newT = centerY - length/2;
        int newR = centerX + length/2;
        int newB = centerY + length/2;
        leftRegion = createLeftRegion();
        upRegion = createUpRegion();
        downRegion = createDownRegion();
        rightRegion = createRightRegion();
        centerRegion = createCenterRegion();
        outerRegion = createOuterRegion();
        v.layout(newL, newT, newR, newB);
    }

    @Override
    public boolean performClick() {
        return super.performClick();
    }



    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        Point point = new Point();
        point.x = (int) motionEvent.getX();
        point.y = (int) motionEvent.getY();

        if(motionEvent.getAction() == MotionEvent.ACTION_DOWN)
        {
            if (!outerRegion.contains(point.x, point.y)) return true;

            if(centerRegion.contains(point.x, point.y))
            {
                currentAction = Action.center;
                image.setBackgroundResource(dpad_ok_resId);
            }
            else if(downRegion.contains(point.x, point.y))
            {
                currentAction = Action.down;
                image.setBackgroundResource(dpad_down_resId);
            }
            else if(upRegion.contains(point.x, point.y))
            {
                currentAction = Action.up;
                image.setBackgroundResource(dpad_up_resId);
            }
            else if(rightRegion.contains(point.x, point.y))
            {
                currentAction = Action.right;
                image.setBackgroundResource(dpad_right_resId);
            }
            else if(leftRegion.contains(point.x, point.y))
            {
                currentAction = Action.left;
                image.setBackgroundResource(dpad_left_resId);
            }
            handler.postDelayed(onTap, ViewConfiguration.getTapTimeout());
        }

        if((motionEvent.getAction() == MotionEvent.ACTION_UP)){
            handler.removeCallbacks(onLongPressed);
            handler.removeCallbacks(onTap);
            if (firstResponseLongPressed){
                firstResponseLongPressed = false;
                Log.i("ACTION_UP", "Long press action up!");
                if (currentAction!=null) delegate.didTouchOnEnd(currentAction);
            } else {
                if (currentAction!=null) delegate.didTouchOn(currentAction);
                Log.i("ACTION_UP", "Tap press!");
            }
            image.setBackgroundResource(dpad_default_resId);
        }

        return true;
    }

    private Region createCenterRegion() {
        return createCircleRegion(centerX,centerY,length/4);
    }

    private Region createOuterRegion() {
        return createCircleRegion(centerX,centerY,length/2);
    }

    private Region createLeftRegion() {
        return createSectorRegion(-225,225,centerX,centerY,length);
    }

    private Region createRightRegion() {
        return createSectorRegion(45,-45,centerX,centerY,length);
    }

    private Region createDownRegion() {
        return createSectorRegion(135,45,centerX,centerY,length);
    }

    private Region createUpRegion() {
        return createSectorRegion(-45,-135,centerX,centerY,length);
    }

    @SuppressWarnings("unused drawing function")
    private Region createCenterRegion(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
        canvas.drawCircle(centerX, centerY, length/4, paint);
        return createCircleRegion(centerX,centerY,length/4);
    }

    private Region createCircleRegion(int centerX,int centerY,int length){
        Path centerPath = new Path();
        centerPath.moveTo(centerX,centerY);
        centerPath.addCircle(centerX,centerY,length,Path.Direction.CCW);
        RectF rectF = new RectF();
        centerPath.computeBounds(rectF, true);
        Region region = new Region();
        region.setPath(centerPath, new Region((int) rectF.left, (int) rectF.top, (int) rectF.right, (int) rectF.bottom));
        return  region;
    }

    @SuppressWarnings("unused drawing function")
    private Region createSectorRegion(Canvas cavas,int startAngle,int sweepAngle,int centerX,int centerY,int length){
        RectF square = new RectF(centerX-length,centerY-length,centerX+length,centerY+length);
        Path path = createSectorPathBounded(square,startAngle,sweepAngle,centerX,centerY,length);

        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(4.0f);
        cavas.drawPath(path,paint);

        return createSectorRegion(square,startAngle,sweepAngle,centerX,centerY,length);
    }

    private Region createSectorRegion(RectF square,int startAngle,int sweepAngle,int centerX,int centerY,int length){
        Path path = createSectorPathBounded(square,startAngle,sweepAngle,centerX,centerY,length);
        Region region = new Region();
        region.setPath(path, new Region((int) square.left, (int) square.top, (int) square.right, (int) square.bottom));
        return region;
    }

    private Region createSectorRegion(int startAngle,int sweepAngle,int centerX,int centerY,int length){
        RectF square = new RectF(centerX-length,centerY-length,centerX+length,centerY+length);
        return createSectorRegion(square,startAngle,sweepAngle,centerX,centerY,length);
    }

    private Path createSectorPathBounded(RectF square,int startAngle,int sweepAngle,int centerX,int centerY,int length){
        Path path = new Path();
        path.moveTo(centerX, centerY);
        path.lineTo((float)(centerX+length*Math.cos(startAngle* Math.PI / 180)),
                (float)(centerY+length*Math.sin(startAngle* Math.PI / 180)));
        path.lineTo((float)(centerX+length*Math.cos(sweepAngle* Math.PI / 180)),
                (float)(centerY+length*Math.sin(sweepAngle* Math.PI / 180)));
        path.close();
        path.addArc(square, startAngle, sweepAngle - startAngle);
        return path;
    }

    public void setDelegate(CircularButtonDelegate delegate) {
        this.delegate = delegate;
    }
}


