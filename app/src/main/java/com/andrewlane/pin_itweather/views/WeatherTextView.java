package com.andrewlane.pin_itweather.views;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by andrewlane on 12/14/16.
 */

public class WeatherTextView extends TextView {

    public WeatherTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public WeatherTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public WeatherTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (!isInEditMode()) {
            Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "weathericons.ttf");
            setTypeface(tf);
        }
    }
}