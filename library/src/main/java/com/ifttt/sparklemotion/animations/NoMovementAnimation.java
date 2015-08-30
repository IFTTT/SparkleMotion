package com.ifttt.sparklemotion.animations;

import com.ifttt.sparklemotion.Animation;

import android.view.View;

/**
 * {@link Animation} subclass that simply takes the <code>offset</code> from
 * {@link #onAnimate(View, float, float)} and apply that to the target view, so that the view will
 * stay where it is during the page scrolling.
 */
public class NoMovementAnimation extends Animation {

    public NoMovementAnimation() {
        this(ALL_PAGES);
    }

    public NoMovementAnimation(int page) {
        super(page, page);
    }

    public NoMovementAnimation(int start, int end) {
        super(start, end);
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        v.setTranslationX(offsetInPixel);
    }
}
