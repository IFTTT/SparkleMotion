package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes View's scale x and y from a value to another.
 */
public class ScaleAnimation extends Animation {

    private float mOutScaleX;
    private float mOutScaleY;

    private float mInScaleX;
    private float mInScaleY;

    public ScaleAnimation(float outScaleX, float outScaleY, float inScaleX, float inScaleY) {
        this(ALL_PAGES, outScaleX, outScaleY, inScaleX, inScaleY);
    }

    public ScaleAnimation(int page, float outScale, float inScale) {
        this(page, outScale, outScale, inScale, inScale);
    }

    public ScaleAnimation(int page, float outScaleX, float outScaleY, float inScaleX,
            float inScaleY) {
        this(page, page, outScaleX, outScaleY, inScaleX, inScaleY);
    }

    public ScaleAnimation(int start, int end, float outScaleX, float outScaleY, float inScaleX,
            float inScaleY) {
        super(start, end);

        this.mOutScaleX = outScaleX;
        this.mOutScaleY = outScaleY;

        this.mInScaleX = inScaleX;
        this.mInScaleY = inScaleY;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);

        v.setScaleX(mInScaleX + offset * (mOutScaleX - mInScaleX));
        v.setScaleY(mInScaleY + offset * (mOutScaleY - mInScaleY));
    }
}
