package com.ifttt.sparklemotion.animations;

import android.view.View;
import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that runs a more complex animation, which scales the view down and
 * fade out the view to a certain value as the ViewPager scrolls, and return back to original values
 * when the page is scrolled.
 * <p/>
 * Implementation details are from
 * http://developer.android.com/training/animation/screen-slide.html#pagetransformer.
 */
public class ZoomOutAnimation extends Animation {
    private static final float DEFAULT_MIN_SCALE = 0.85f;
    private static final float DEFAULT_MIN_ALPHA = 0.5f;

    private final float mMinScale;
    private final float mMinAlpha;

    /**
     * Base constructor of the class, accepting common information about the animation to this
     * instance.
     *
     * @param start Page index that this animation should start.
     * @param end Page index that this animation should stop.
     * @param minScale Minimum scale X and Y of the View when animating.
     * @param minAlpha Minimum alpha of the View when animating.
     */
    public ZoomOutAnimation(int start, int end, float minScale, float minAlpha) {
        super(start, end);

        mMinAlpha = minAlpha;
        mMinScale = minScale;
    }

    public ZoomOutAnimation(int start, int end) {
        this(start, end, DEFAULT_MIN_SCALE, DEFAULT_MIN_ALPHA);
    }

    public ZoomOutAnimation(int page) {
        this(page, page);
    }

    @Override
    public void onAnimateOffScreenLeft(View v, float offset, float offsetInPixel) {
        v.setAlpha(0f);
    }

    @Override
    public void onAnimateOffScreenRight(View v, float offset, float offsetInPixel) {
        v.setAlpha(0f);
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        int pageWidth = v.getWidth();
        int pageHeight = v.getHeight();

        float scaleFactor = Math.max(mMinScale, 1 - Math.abs(offset));
        float vertMargin = pageHeight * (1 - scaleFactor) / 2;
        float horzMargin = pageWidth * (1 - scaleFactor) / 2;

        if (offset < 0) {
            v.setTranslationX(horzMargin - vertMargin / 2);
        } else {
            v.setTranslationX(-horzMargin + vertMargin / 2);
        }

        // Scale the page down (between MIN_SCALE and 1)
        v.setScaleX(scaleFactor);
        v.setScaleY(scaleFactor);

        // Fade the page relative to its size.
        v.setAlpha(mMinAlpha + (scaleFactor - mMinScale) / (1 - mMinScale) * (1 - mMinAlpha));
    }
}
