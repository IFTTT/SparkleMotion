package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes the View's translation x and y.
 */
public class TranslationAnimation extends Animation {

    private final float mTranslationX;
    private final float mTranslationY;

    /**
     * Flag to set whether this animation should be relative to the scrolling page or not. If set to true, the View
     * being animated will ignore the scrolling of the parent View.
     */
    private final boolean mAbsolute;

    private boolean mOriginalTranslationSet;
    private float mOriginalTranslationX;
    private float mOriginalTranslationY;

    public TranslationAnimation(
            int start, int end, boolean absolute,
            float translationX,
            float translationY) {
        super(start, end);
        this.mAbsolute = absolute;
        this.mTranslationX = translationX;
        this.mTranslationY = translationY;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        if (!mAbsolute) {
            offsetInPixel = 0;
        }

        if (!mOriginalTranslationSet) {
            // Store the original translation X and Y when the animation first starts.
            mOriginalTranslationX = v.getTranslationX();
            mOriginalTranslationY = v.getTranslationY();
            mOriginalTranslationSet = true;
        }

        offset = Math.abs(offset);

        v.setTranslationX(mOriginalTranslationX + offset * (mTranslationX - mOriginalTranslationX) + offsetInPixel);
        v.setTranslationY(mOriginalTranslationY + offset * (mTranslationY - mOriginalTranslationY));
    }
}
