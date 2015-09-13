package com.ifttt.sparklemotion.animations;

import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Page;

import android.view.View;

/**
 * {@link Animation} subclass that simply takes the <code>offset</code> from
 * {@link #onAnimate(View, float, float)} and apply that to the target view, so that the view will
 * stay where it is during the page scrolling.
 */
public class NoMovementAnimation extends Animation {

    /**
     * Constructor for building an NoMovementAnimation for a range of pages.
     *
     * @param page Page object with specific page information about this animation.
     */
    public NoMovementAnimation(Page page) {
        super(page);
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        v.setTranslationX(offsetInPixel);
    }
}
