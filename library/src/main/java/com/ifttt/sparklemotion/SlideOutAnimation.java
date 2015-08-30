package com.ifttt.sparklemotion;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * {@link Animation} subclass used for running slide out animation when {@link Decor#slideOut} is true.
 * Note that this animation has state, therefore cannot be reused by other Views, each View needs to have
 * an instance of it.
 */
final class SlideOutAnimation extends Animation {

    private boolean mOriginalTranslationSet;

    /**
     * Distance of the View to slide out of the screen.
     */
    private float mDistance;

    /**
     * Translation X of the View before running this animation.
     */
    private float mOriginalTranslationX;

    public SlideOutAnimation(int page) {
        super(page);
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        if (!mOriginalTranslationSet) {
            mOriginalTranslationX = v.getTranslationX();
            mDistance = -(v.getLeft() + v.getWidth() * v.getScaleX());
            mOriginalTranslationSet = true;
        }

        offset = Math.abs(offset);
        v.setTranslationX(mOriginalTranslationX + offset * mDistance);

        Log.d(getClass().getSimpleName(), offset + " ");
    }
}
