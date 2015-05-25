package com.ifttt.jazzhands.animations;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes View's scale x and y from a value to another.
 */
public class ScaleAnimation extends Animation {

    private float mFromScaleX;
    private float mFromScaleY;

    private float mToScaleX;
    private float mToScaleY;


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
        super(start, end, false);

        this.mFromScaleX = outScaleX;
        this.mFromScaleY = outScaleY;

        this.mToScaleX = inScaleX;
        this.mToScaleY = inScaleY;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        fraction = Math.abs(fraction);

        v.setScaleX(mFromScaleX + fraction * (mToScaleX - mFromScaleX));
        v.setScaleY(mFromScaleY + fraction * (mToScaleY - mFromScaleY));
    }
}
