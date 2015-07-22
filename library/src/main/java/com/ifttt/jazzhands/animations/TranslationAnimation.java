package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes the View's translation x and y.
 */
public class TranslationAnimation extends Animation {

    private float mTranslationX;
    private float mTranslationY;

    private boolean mOriginalTranslationSet;
    private float mOriginalTranslationX;
    private float mOriginalTranslationY;

    public TranslationAnimation(
            int start, int end, boolean absolute,
            float translationX,
            float translationY) {
        super(start, end, absolute);
        this.mTranslationX = translationX;
        this.mTranslationY = translationY;
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        if (!absolute) {
            offset = 0;
        }

        if (!mOriginalTranslationSet) {
            mOriginalTranslationX = v.getTranslationX();
            mOriginalTranslationY = v.getTranslationY();
            mOriginalTranslationSet = true;
        }

        fraction = Math.abs(fraction);

        v.setTranslationX(mOriginalTranslationX + fraction * (mTranslationX - mOriginalTranslationX) + offset);
        v.setTranslationY(mOriginalTranslationY + fraction * (mTranslationY - mOriginalTranslationY));
    }
}
