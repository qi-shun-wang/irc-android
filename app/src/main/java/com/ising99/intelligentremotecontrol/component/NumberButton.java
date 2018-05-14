package com.ising99.intelligentremotecontrol.component;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

public class NumberButton extends android.support.v7.widget.AppCompatButton {
    public NumberButton(Context context) {
        super(context);
        init();
    }

    public NumberButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public NumberButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }



    private void init(){
        Typeface font_type = Typeface.createFromAsset(getContext().getAssets(), "font/gothic.ttf");
        setTypeface(font_type);
    }
}
