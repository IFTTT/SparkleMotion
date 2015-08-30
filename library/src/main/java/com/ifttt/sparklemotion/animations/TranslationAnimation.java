package com.ifttt.sparklemotion.animations;

import android.util.Log;
import android.view.View;

import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that changes the View's translation x and y.
 */
public class TranslationAnimation extends Animation {
    private final float mInTranslationX;
    private final float mInTranslationY;
    private final float mOutTranslationX;
    private final float mOutTranslationY;

    /**
     * Flag to set whether this animation should be relative to the scrolling page or not. If set
     * to true, the View being animated will ignore the scrolling of the parent View.
     */
    private final boolean mAbsolute;

    public TranslationAnimation(int page, boolean absolute, float translationX, float translationY) {
        this(page, page, absolute, 0, 0, translationX, translationY);
    }

    public TranslationAnimation(int start, int end, boolean absolute, float translationX, float translationY) {
        this(start, end, absolute, 0, 0, translationX, translationY);
    }

    public TranslationAnimation(int page, boolean absolute, float inTranslationX,
            float inTranslationY, float outTranslationX, float outTranslationY) {
        this(page, page, absolute, inTranslationX, inTranslationY, outTranslationX, outTranslationY);
    }

    public TranslationAnimation(int start, int end, boolean absolute, float inTranslationX,
            float inTranslationY, float outTranslationX, float outTranslationY) {
        super(start, end);
        mAbsolute = absolute;
        mInTranslationX = inTranslationX;
        mInTranslationY = inTranslationY;
        mOutTranslationX = outTranslationX;
        mOutTranslationY = outTranslationY;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        if (!mAbsolute) {
            offsetInPixel = 0;
        }

        offset = Math.abs(offset);
        v.setTranslationX(mInTranslationX + offset * (mOutTranslationX - mInTranslationX) + offsetInPixel);
        v.setTranslationY(mInTranslationY + offset * (mOutTranslationY - mInTranslationY));
    }
}
