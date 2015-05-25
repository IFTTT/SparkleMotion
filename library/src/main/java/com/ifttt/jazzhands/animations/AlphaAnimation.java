package com.ifttt.jazzhands.animations;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes alpha of the View.
 */
public class AlphaAnimation extends Animation {

    private float mOutAlpha;
    private float mInAlpha;

    public AlphaAnimation(int page, float outAlpha, float inAlpha) {
        this(page, page, outAlpha, inAlpha);
    }

    public AlphaAnimation(int start, int end, float outAlpha, float inAlpha) {
        super(start, end, false);

        this.mOutAlpha = outAlpha;
        this.mInAlpha = inAlpha;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        fraction = Math.abs(fraction);
        v.setAlpha(mOutAlpha + fraction * (mInAlpha - mOutAlpha));
    }
}
