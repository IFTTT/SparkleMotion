package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes the view's translation by a {@link #mFactor} for parallax
 * effect.
 */
public class ParallaxAnimation extends Animation {

    private float mFactor;

    public ParallaxAnimation(int page, float factor) {
        this(page, page, factor);
    }

    public ParallaxAnimation(int start, int end, float factor) {
        super(start, end);

        mFactor = factor;
    }

    @Override
    protected void onAnimate(View v, float fraction, float offset) {
        int pageWidth = v.getWidth();

        v.setTranslationX(pageWidth * -fraction / mFactor);
    }
}
