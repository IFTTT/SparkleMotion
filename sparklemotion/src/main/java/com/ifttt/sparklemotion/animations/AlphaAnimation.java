package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Page;

/**
 * Subclass of {@link Animation} that changes alpha of the View.
 */
public class AlphaAnimation extends Animation {

    private float mOutAlpha;
    private float mInAlpha;

    /**
     * Constructor for building an AlphaAnimation for a range of pages.
     *
     * @param page      Page object with specific page information about this animation.
     * @param outAlpha  Alpha value when the View is not visible, i.e. the page is scrolled to the left or right
     *                  of the primary page.
     * @param inAlpha   Alpha value when the View is visible as the primary page.
     */
    public AlphaAnimation(Page page, float outAlpha, float inAlpha) {
        super(page);

        this.mOutAlpha = outAlpha;
        this.mInAlpha = inAlpha;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);
        v.setAlpha(mInAlpha + offset * (mOutAlpha - mInAlpha));
    }
}
