package com.ifttt.jazzhands.animations;

import android.view.View;

/**
 * {@link Animation} subclass that simply takes the {@code offset} from {@link #onAnimate(View, float, float)}
 * and apply that to the target view, so that the view will stay where it is during the page scrolling.
 *
 * This animation by default sets {@code absolute} to true.
 */
public class NoMovementAnimation extends Animation {

    public NoMovementAnimation(int page) {
        this(page, page);
    }

    public NoMovementAnimation(int start, int end) {
        super(start, end, true);
    }

    @Override
    protected void onAnimate(View v, float fraction, float offset) {
        v.setTranslationX(offset);
    }
}
