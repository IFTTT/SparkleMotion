package com.ifttt.jazzhands.animations;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes View's scale x and y from a value to another.
 */
public class ScaleAnimation extends Animation {

    private float mFromScaleX;
    private float mFromScaleY;

    private float mToScaleX;
    private float mToScaleY;


    public ScaleAnimation(int page, float fromScale, float toScale) {
        this(page, fromScale, fromScale, toScale, toScale);
    }

    public ScaleAnimation(int page,
                          float fromScaleX, float fromScaleY, float toScaleX, float toScaleY) {
        this(page, page, fromScaleX, fromScaleY, toScaleX, toScaleY);
    }

    public ScaleAnimation(int start, int end, float fromScale, float toScale) {
        this(start, end, fromScale, fromScale, toScale, toScale);
    }

    public ScaleAnimation(int start, int end,
                          float fromScaleX, float fromScaleY, float toScaleX, float toScaleY) {
        super(start, end, false);

        this.mFromScaleX = fromScaleX;
        this.mFromScaleY = fromScaleY;

        this.mToScaleX = toScaleX;
        this.mToScaleY = toScaleY;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        v.setScaleX(mFromScaleX + fraction * (mToScaleX - mFromScaleX));
        v.setScaleY(mFromScaleY + fraction * (mToScaleY - mFromScaleY));
    }
}
