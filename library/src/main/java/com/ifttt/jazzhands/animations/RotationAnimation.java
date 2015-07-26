package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes View's rotation.
 */
public class RotationAnimation extends Animation {

    private float mRotationX;
    private float mRotationY;
    private float mRotation;

    public RotationAnimation(int page, float rotation) {
        this(page, page, rotation);
    }

    public RotationAnimation(int start, int end, float rotation) {
        super(start, end);

        this.mRotation = rotation;
    }

    public RotationAnimation(int page, float rotationX, float rotationY) {
        this(page, page, rotationX, rotationY);
    }

    public RotationAnimation(int start, int end, float rotationX, float rotationY) {
        super(start, end);

        this.mRotationX = rotationX;
        this.mRotationY = rotationY;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        offset = Math.abs(offset);

        if (mRotationX > 0) {
            v.setRotationX(offset * mRotationX);
        }

        if (mRotationY > 0) {
            v.setRotationY(offset * mRotationY);
        }

        if (mRotation > 0) {
            v.setRotation(offset * mRotation);
        }

    }
}
