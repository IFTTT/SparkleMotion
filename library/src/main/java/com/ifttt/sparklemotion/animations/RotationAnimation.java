package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes View's rotation.
 */
public class RotationAnimation extends Animation {

    private float mRotation;

    public RotationAnimation(float rotation) {
        this(ALL_PAGES, rotation);
    }

    public RotationAnimation(int page, float rotation) {
        this(page, page, rotation);
    }

    public RotationAnimation(int start, int end, float rotation) {
        super(start, end);

        this.mRotation = rotation;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);

        if (mRotation > 0) {
            v.setRotation(offset * mRotation);
        }
    }
}
