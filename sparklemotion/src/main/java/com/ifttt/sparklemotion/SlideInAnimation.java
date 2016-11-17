package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewTreeObserver;

/**
 * {@link Animation} subclass used for running slide out animation when {@link Decor.Builder#slideIn()} is used.
 * Note that this animation has state, therefore cannot be reused by other Views, each View needs to have
 * a new instance of it.
 */
final class SlideInAnimation extends Animation {

    private boolean mOriginalTranslationSet;

    /**
     * Distance of the View to slide out of the screen.
     */
    float mDistance;

    /**
     * Translation X of the View before running this animation.
     */
    float mOriginalTranslationX;

    public SlideInAnimation(@NonNull Page page) {
        super(page);
    }

    @Override
    public void onAnimate(View view, float offset, float offsetInPixel) {
        if (!mOriginalTranslationSet) {
            mOriginalTranslationSet = true;
            initViewPosition(view, 1 - offset);
        }

        offset = Math.abs(offset);
        view.setTranslationX(mOriginalTranslationX + (1 - offset) * mDistance);
    }

    /**
     * Initialize the View's position by adding a {@link android.view.ViewTreeObserver.OnPreDrawListener}, listening to
     * pre-draw, then assign the initial frame and destination frame.
     *
     * @param view   View to be animated.
     * @param offset Initial offset.
     */
    private void initViewPosition(final View view, final float offset) {
        view.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                view.getViewTreeObserver().removeOnPreDrawListener(this);
                mOriginalTranslationX = view.getTranslationX();
                View parent = (View) view.getParent();
                if (parent == null) {
                    return false;
                }

                mDistance = parent.getWidth() - view.getLeft();

                // Once initialized, run the initial animation frame.
                view.setTranslationX(mOriginalTranslationX + Math.abs(offset) * mDistance);
                return false;
            }
        });
    }
}
