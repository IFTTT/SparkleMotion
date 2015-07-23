package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes View's scale x and y from a value to another.
 */
public class ScaleAnimation extends Animation {

    private float mOutScaleX;
    private float mOutScaleY;

    private float mInScaleX;
    private float mInScaleY;


    public ScaleAnimation(int page, float outScale, float inScale) {
        this(page, outScale, outScale, inScale, inScale);
    }

    public ScaleAnimation(int page,
                          float outScaleX, float outScaleY, float inScaleX, float inScaleY) {
        this(page, page, outScaleX, outScaleY, inScaleX, inScaleY);
    }

    public ScaleAnimation(int start, int end, float outScale, float inScale) {
        this(start, end, outScale, outScale, inScale, inScale);
    }

    public ScaleAnimation(int start, int end,
                          float outScaleX, float outScaleY, float inScaleX, float inScaleY) {
        super(start, end);

        this.mOutScaleX = outScaleX;
        this.mOutScaleY = outScaleY;

        this.mInScaleX = inScaleX;
        this.mInScaleY = inScaleY;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        fraction = Math.abs(fraction);

        v.setScaleX(mInScaleX + fraction * (mOutScaleX - mInScaleX));
        v.setScaleY(mInScaleY + fraction * (mOutScaleY - mInScaleY));
    }
}
