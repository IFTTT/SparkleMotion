package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.view.View;

/**
 * {@link Animation} subclass that simply takes the <code>offset</code> from {@link #onAnimate(View, float, float)}
 * and apply that to the target view, so that the view will stay where it is during the page scrolling.
 *
 */
public class NoMovementAnimation extends Animation {

    public NoMovementAnimation(int page, boolean absolute) {
        this(page, page, absolute);
    }

    public NoMovementAnimation(int start, int end, boolean absolute) {
        super(start, end);
    }

    @Override
    protected void onAnimate(View v, float offset, float offsetInPixel) {
        v.setTranslationX(offsetInPixel);
    }
}
