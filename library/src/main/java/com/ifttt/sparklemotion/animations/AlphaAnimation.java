package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes alpha of the View.
 */
public class AlphaAnimation extends Animation {

    private float mOutAlpha;
    private float mInAlpha;

    /**
     * Constructor for building an AlphaAnimation for all pages. This should be used for ViewPager View animations,
     * as they will also be involved in ViewPager scrolling, therefore making them invisible once they are scrolled
     * to left or right.
     */
    public AlphaAnimation(float outAlpha, float inAlpha) {
        this(ALL_PAGES, outAlpha, inAlpha);
    }

    /**
     * Constructor for building an AlphaAnimation for a specific page. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page Page index that this animation should run on.
     */
    public AlphaAnimation(int page, float outAlpha, float inAlpha) {
        this(page, page, outAlpha, inAlpha);
    }

    /**
     * Constructor for building an AlphaAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param start Page index that this animation should start.
     * @param end   Page index that this animation should end.
     * @param outAlpha  Alpha value when the View is not visible, i.e. the page is scrolled to the left or right
     *                  of the primary page.
     * @param inAlpha   Alpha value when the View is visible as the primary page.
     */
    public AlphaAnimation(int start, int end, float outAlpha, float inAlpha) {
        super(start, end);

        this.mOutAlpha = outAlpha;
        this.mInAlpha = inAlpha;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);
        v.setAlpha(mInAlpha + offset * (mOutAlpha - mInAlpha));
    }
}
