package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes the view's translation by a {@link #mFactor} for
 * parallax effect.
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
    public void onAnimate(View v, float offset, float offsetInPixel) {
        int pageWidth = v.getWidth();

        v.setTranslationX(pageWidth * -offset / mFactor);
    }
}
