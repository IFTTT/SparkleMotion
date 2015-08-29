package com.ifttt.sparklemotion;

import android.view.View;
import android.view.ViewGroup;

final class SlideOutAnimation extends Animation {
    private boolean mOriginalTranslationSet;
    private float mDistance;
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

        v.setTranslationX(mOriginalTranslationX + offset * mDistance);
    }
}
