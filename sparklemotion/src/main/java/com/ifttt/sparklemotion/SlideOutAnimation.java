package com.ifttt.sparklemotion;

import android.view.View;
import android.view.ViewTreeObserver;

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

    public SlideOutAnimation(Page page) {
        super(page);
    }

    @Override
    public void onAnimate(final View view, float offset, float offsetInPixel) {
        if (!mOriginalTranslationSet) {
            mOriginalTranslationSet = true;
            initViewPosition(view, offset);
        }

        offset = Math.abs(offset);
        view.setTranslationX(mOriginalTranslationX + offset * mDistance);
    }

    private void initViewPosition(final View view, final float offset) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                mOriginalTranslationX = view.getTranslationX();
                mDistance = -(view.getLeft() + view.getWidth() * view.getScaleX());
                view.setTranslationX(mOriginalTranslationX + Math.abs(offset) * mDistance);
                return false;
            }
        });
    }
}
