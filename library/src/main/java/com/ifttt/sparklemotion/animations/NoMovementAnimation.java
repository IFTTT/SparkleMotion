package com.ifttt.sparklemotion.animations;

import com.ifttt.sparklemotion.Animation;

import android.view.View;

/**
 * {@link Animation} subclass that simply takes the <code>offset</code> from
 * {@link #onAnimate(View, float, float)} and apply that to the target view, so that the view will
 * stay where it is during the page scrolling.
 */
public class NoMovementAnimation extends Animation {

    /**
     * Constructor for building an NoMovementAnimation for all pages. This should be used for ViewPager View animations,
     * as they will also be involved in ViewPager scrolling, therefore making them invisible once they are scrolled to
     * left or right.
     */
    public NoMovementAnimation() {
        this(ALL_PAGES);
    }

    /**
     * Constructor for building an NoMovementAnimation for a specific page. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page Page index that this animation should run on.
     */
    public NoMovementAnimation(int page) {
        super(page, page);
    }

    /**
     * Constructor for building an NoMovementAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param start Page index that this animation should start.
     * @param end   Page index that this animation should end.
     */
    public NoMovementAnimation(int start, int end) {
        super(start, end);
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        v.setTranslationX(offsetInPixel);
    }
}
