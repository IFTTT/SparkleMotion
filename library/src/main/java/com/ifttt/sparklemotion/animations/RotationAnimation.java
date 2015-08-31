package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes View's rotation.
 */
public class RotationAnimation extends Animation {

    private final float mInRotation;
    private final float mOutRotation;

    public RotationAnimation(float inRotation, float outRotation) {
        this(ALL_PAGES, inRotation, outRotation);
    }

    public RotationAnimation(int page, float inRotation, float outRotation) {
        this(page, page, inRotation, outRotation);
    }

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
