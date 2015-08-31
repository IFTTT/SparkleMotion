package com.ifttt.sparklemotion.animations;

import android.view.View;

import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes the view's translation by a {@link #mFactor} for
 * parallax effect.
 */
public class ParallaxAnimation extends Animation {

    private float mFactor;

    /**
     * Constructor for building a ParallaxAnimation for all pages. This should be used for ViewPager View animations,
     * as they will also be involved in ViewPager scrolling, therefore making them invisible once they are scrolled to
     * left or right.
     */
    public ParallaxAnimation(float factor) {
        this(ALL_PAGES, factor);
    }

    /**
     * Constructor for building a ParallaxAnimation for a specific page. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page Page index that this animation should run on.
     */
    public ParallaxAnimation(int page, float factor) {
        this(page, page, factor);
    }

    /**
     * Constructor for building a ParallaxAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param start  Page index that this animation should start.
     * @param end    Page index that this animation should end.
     * @param factor Parallax factor used to adjust the translationX of the View.
     */
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
