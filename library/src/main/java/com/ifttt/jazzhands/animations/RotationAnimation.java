package com.ifttt.jazzhands.animations;

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
        super(start, end, false);

        this.mRotation = rotation;
    }

    public RotationAnimation(int page, float rotationX, float rotationY) {
        this(page, page, rotationX, rotationY);
    }

    public RotationAnimation(int start, int end, float rotationX, float rotationY) {
        super(start, end, false);

        this.mRotationX = rotationX;
        this.mRotationY = rotationY;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        fraction = Math.abs(fraction);

        if (mRotationX > 0) {
            v.setRotationX(fraction * mRotationX);
        }

        if (mRotationY > 0) {
            v.setRotationY(fraction * mRotationY);
        }

        if (mRotation > 0) {
            v.setRotation(fraction * mRotation);
        }

    }
}
