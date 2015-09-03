package com.ifttt.sparklemotion.animations;

import android.view.View;

import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes View's rotation.
 */
public class RotationAnimation extends Animation {

    private final float mInRotation;
    private final float mOutRotation;

    /**
     * Constructor for building a RotationAnimation for all pages. This should be used for ViewPager View animations,
     * as they will also be involved in ViewPager scrolling, therefore making them invisible once they are scrolled
     * to left or right.
     */
    public RotationAnimation(float inRotation, float outRotation) {
        this(ALL_PAGES, inRotation, outRotation);
    }

    /**
     * Constructor for building a RotationAnimation for a specific page. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page Page index that this animation should run on.
     */
    public RotationAnimation(int page, float inRotation, float outRotation) {
        this(page, page, inRotation, outRotation);
    }

    /**
     * Constructor for building a RotationAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param start       Page index that this animation should start.
     * @param end         Page index that this animation should end.
     * @param inRotation  Rotation value when the page of the View is currently primary page.
     * @param outRotation Rotation value when the page of the View is not visible, i.e. the page is scrolled to either
     *                    left or right of the primary page.
     */
    public RotationAnimation(int start, int end, float inRotation, float outRotation) {
        super(start, end);

        mInRotation = inRotation;
        mOutRotation = outRotation;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);

        v.setRotation(mInRotation + offset * (mOutRotation - mInRotation));
    }
}
