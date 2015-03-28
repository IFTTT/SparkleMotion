package com.ifttt.jazzhands.animations;

import android.util.Log;
import android.view.View;

/**
 * Subclass of {@link Animation} that changes alpha of the View.
 */
public class AlphaAnimation extends Animation {

    private float mFromAlpha;
    private float mToAlpha;

    public AlphaAnimation(int page, float fromAlpha, float toAlpha) {
        this(page, page, fromAlpha, toAlpha);
    }

    public AlphaAnimation(int start, int end, float fromAlpha, float toAlpha) {
        super(start, end, false);

        this.mFromAlpha = fromAlpha;
        this.mToAlpha = toAlpha;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        Log.d(getClass().getSimpleName(), fraction + " ");
        v.setAlpha(mFromAlpha + fraction * (mToAlpha - mFromAlpha));
    }
}
