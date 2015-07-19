package com.ifttt.jazzhands.animations;

import android.view.View;

import com.ifttt.jazzhands.Animation;

/**
 * {@link Animation} subclass that simply takes the {@code offset} from {@link #onAnimate(View, float, float)}
 * and apply that to the target view, so that the view will stay where it is during the page scrolling.
 *
 */
public class NoMovementAnimation extends Animation {

    public NoMovementAnimation(int page, boolean absolute) {
        this(page, page, absolute);
    }

    public NoMovementAnimation(int start, int end, boolean absolute) {
        super(start, end, absolute);
    }

    @Override
    protected void onAnimate(View v, float fraction, float offset) {
        v.setTranslationX(offset);
    }
}
