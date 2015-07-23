package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes alpha of the View.
 */
public class AlphaAnimation extends Animation {

    private float mOutAlpha;
    private float mInAlpha;

    public AlphaAnimation(int page, float outAlpha, float inAlpha) {
        super(page, page);

        this.mOutAlpha = outAlpha;
        this.mInAlpha = inAlpha;
    }

    public AlphaAnimation(int start, int end, float outAlpha, float inAlpha) {
        super(start, end);

        this.mOutAlpha = outAlpha;
        this.mInAlpha = inAlpha;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        fraction = Math.abs(fraction);
        v.setAlpha(mInAlpha + fraction * (mOutAlpha - mInAlpha));
    }
}
