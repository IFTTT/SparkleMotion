package com.ifttt.sparklemotion.animations;

import android.view.View;

import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Decor;
import com.ifttt.sparklemotion.Page;

/**
 * Subclass of {@link Animation} that changes the View's translation x and y.
 */
public class TranslationAnimation extends Animation {

    private final float mInTranslationX;
    private final float mInTranslationY;
    private final float mOutTranslationX;
    private final float mOutTranslationY;

    private final boolean mAbsolute;

    /**
     * Constructor for building a TranslationAnimation that animates in a range of pages.
     * <p/>
     * Note that for animating {@link Decor}, {@code absolute} will be ignored and always be true, meaning that the
     * Decor content View will only animate the translation X value given by the animation, instead of the combination
     * of the translation X value of the animation and ViewPager scrolling.
     *
     * @param page            Page object with specific page information about this animation.
     * @param inTranslationX  TranslationX when the page of the View is the primary page, i.e. the page is the
     *                        current page and the ViewPager is not scrolling.
     * @param inTranslationY  TranslationY when the page of the View is the primary page, i.e. the page is the
     *                        current page and the ViewPager is not scrolling.
     * @param outTranslationX TranslationX when the page of the View is not visible, i.e. the page is scrolled to
     *                        the left or right of the primary page.
     * @param outTranslationY TranslationY when the page of the View is not visible, i.e. the page is scrolled to
     *                        the left or right of the primary page.
     * @param absolute        Flag to set whether this animation should be relative to the scrolling page or not. If set
     *                        to true, the View being animated will ignore the scrolling of the parent View.
     */
    public TranslationAnimation(Page page, float inTranslationX,
            float inTranslationY, float outTranslationX, float outTranslationY, boolean absolute) {
        super(page);
        mInTranslationX = inTranslationX;
        mInTranslationY = inTranslationY;
        mOutTranslationX = outTranslationX;
        mOutTranslationY = outTranslationY;
        mAbsolute = absolute;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        if (!mAbsolute) {
            offsetInPixel = 0;
        }

        offset = Math.abs(offset);
        v.setTranslationX(mInTranslationX + offset * (mOutTranslationX - mInTranslationX) + offsetInPixel);
        v.setTranslationY(mInTranslationY + offset * (mOutTranslationY - mInTranslationY));
    }
}
