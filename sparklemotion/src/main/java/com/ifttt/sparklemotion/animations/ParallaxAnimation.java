package com.ifttt.sparklemotion.animations;

import android.view.View;

import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Page;

/**
 * Subclass of {@link Animation} that changes the view's translation by a {@link #factor} for
 * parallax effect.
 */
public class ParallaxAnimation extends Animation {

    private static final float DEFAULT_FACTOR = 2.0f;

    private float factor;

    /**
     * Constructor for building a ParallaxAnimation for specific {@link Page} with a default factor of 2.0.
     *
     * @param page Page object with specific page information about this animation.
     */
    public ParallaxAnimation(Page page) {
        this(page, DEFAULT_FACTOR);
    }

    /**
     * Constructor for building a ParallaxAnimation for specific {@link Page}.
     *
     * @param page   Page object with specific page information about this animation.
     * @param factor Parallax factor used to adjust the translationX of the View.
     */
    public ParallaxAnimation(Page page, float factor) {
        super(page);

        this.factor = factor;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        int width = v.getWidth();

        v.setTranslationX(width * -offset / factor);
    }
}
