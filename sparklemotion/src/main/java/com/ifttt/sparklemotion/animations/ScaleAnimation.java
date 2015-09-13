package com.ifttt.sparklemotion.animations;

import android.view.View;

import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Page;

/**
 * Subclass of {@link Animation} that changes View's scale x and y from a value to another.
 */
public class ScaleAnimation extends Animation {

    private float mOutScaleX;
    private float mOutScaleY;

    private float mInScaleX;
    private float mInScaleY;

    /**
     * Constructor for building a ScaleAnimation for a range of pages.
     *
     * @param page      Page object with specific page information about this animation.
     * @param outScaleX ScaleX value when the page of the View is not visible, i.e. the page is scrolled to either
     *                  left or right of the primary page.
     * @param outScaleY ScaleY value when the page of the View is not visible, i.e. the page is scrolled to either
     *                  left or right of the primary page.
     * @param inScaleX  ScaleX value when the page of the View is currently primary page.
     * @param inScaleY  ScaleY value when the page of the View is currently primary page.
     */
    public ScaleAnimation(Page page, float inScaleX, float inScaleY, float outScaleX, float outScaleY) {
        super(page);

        this.mInScaleX = inScaleX;
        this.mInScaleY = inScaleY;

        this.mOutScaleX = outScaleX;
        this.mOutScaleY = outScaleY;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);

        v.setScaleX(mInScaleX + offset * (mOutScaleX - mInScaleX));
        v.setScaleY(mInScaleY + offset * (mOutScaleY - mInScaleY));
    }
}
