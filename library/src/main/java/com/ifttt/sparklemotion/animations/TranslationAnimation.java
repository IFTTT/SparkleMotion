package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;

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
     * Constructor for building a TranslationAnimation that animates in all pages with given translation X and Y.
     * This should be used for ViewPager View animations, as they will also be involved in ViewPager scrolling,
     * therefore making them invisible once they are scrolled to left or right.
     */
    public TranslationAnimation(float inTranslationX, float inTranslationY, float outTranslationX,
            float outTranslationY,
            boolean absolute) {
        this(ALL_PAGES, inTranslationX, inTranslationY, outTranslationX, outTranslationY, absolute);
    }

    /**
     * Constructor for building a TranslationAnimation that animates in a specific page. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page Page index that this animation should run on.
     */
    public TranslationAnimation(int page, float inTranslationX,
            float inTranslationY, float outTranslationX, float outTranslationY, boolean absolute) {
        this(page, page, inTranslationX, inTranslationY, outTranslationX, outTranslationY, absolute);
    }

    /**
     * Constructor for building a TranslationAnimation that animates in a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param start Page index that this animation should start.
     * @param end   Page index that this animation should end.
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
    public TranslationAnimation(int start, int end, float inTranslationX,
            float inTranslationY, float outTranslationX, float outTranslationY, boolean absolute) {
        super(start, end);
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
