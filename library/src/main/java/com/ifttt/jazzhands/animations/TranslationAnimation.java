package com.ifttt.jazzhands.animations;

import android.view.View;

/**
 * Subclass of {@link Animation} that changes the View's translation x and y. 
 */
public class TranslationAnimation extends Animation {

    private float mTranslationX;
    private float mTranslationY;

    public TranslationAnimation(int start, int end, boolean absolute,
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

        v.setTranslationX(fraction * mTranslationX + offset);
        v.setTranslationY(fraction * mTranslationY);
    }
}
