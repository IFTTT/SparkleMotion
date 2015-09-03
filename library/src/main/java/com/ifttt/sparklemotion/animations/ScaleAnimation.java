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

    /**
     * Constructor for building a ScaleAnimation for all pages. This should be used for ViewPager View animations,
     * as they will also be involved in ViewPager scrolling, therefore making them invisible once they are scrolled
     * to left or right.
     */
    public ScaleAnimation(float inScaleX, float inScaleY, float outScaleX, float outScaleY) {
        this(ALL_PAGES, inScaleX, inScaleY, outScaleX, outScaleY);
    }

    /**
     * Constructor for building a ScaleAnimation for a specific page. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page Page index that this animation should run on.
     */
    public ScaleAnimation(int page, float inScaleX, float inScaleY, float outScaleX, float outScaleY) {
        this(page, page, inScaleX, inScaleY, outScaleX, outScaleY);
    }

    /**
     * Constructor for building a ScaleAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param start Page index that this animation should start.
     * @param end Page index that this animation should end.
     * @param outScaleX ScaleX value when the page of the View is not visible, i.e. the page is scrolled to either
     * left or right of the primary page.
     * @param outScaleY ScaleY value when the page of the View is not visible, i.e. the page is scrolled to either
     * left or right of the primary page.
     * @param inScaleX ScaleX value when the page of the View is currently primary page.
     * @param inScaleY ScaleY value when the page of the View is currently primary page.
     */
    public ScaleAnimation(int start, int end, float inScaleX, float inScaleY, float outScaleX, float outScaleY) {
        super(start, end);

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
