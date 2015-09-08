package com.ifttt.sparklemotion.animations;

import android.view.View;

import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Page;

/**
 * Subclass of {@link Animation} that changes View's rotation.
 */
public class RotationAnimation extends Animation {

    private final float mInRotation;
    private final float mOutRotation;

    /**
     * Constructor for building a RotationAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page   Page object with specific page information about this animation.
     * @param inRotation  Rotation value when the page of the View is currently primary page.
     * @param outRotation Rotation value when the page of the View is not visible, i.e. the page is scrolled to either
     *                    left or right of the primary page.
     */
    public RotationAnimation(Page page, float inRotation, float outRotation) {
        super(page);

        mInRotation = inRotation;
        mOutRotation = outRotation;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);

        v.setRotation(mInRotation + offset * (mOutRotation - mInRotation));
    }
}
