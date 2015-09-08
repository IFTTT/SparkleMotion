package com.ifttt.sparklemotiondemo;

import android.view.View;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Page;

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

    public ZoomOutAnimation(Page page) {
        this(page, DEFAULT_MIN_SCALE, DEFAULT_MIN_ALPHA);
    }

    public ZoomOutAnimation(Page page, float minScale, float minAlpha) {
        super(page);

        mMinAlpha = minAlpha;
        mMinScale = minScale;
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
