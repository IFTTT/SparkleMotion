package com.ifttt.sparklemotion.animations;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Decor;
import com.ifttt.sparklemotion.Page;

/**
 * Subclass of {@link Animation} that animates the view based on a {@link Path}. It is essentially
 * a translation animation, but the translation x and y is changed to follow the path.
 */
public class PathAnimation extends Animation {

    private final PathMeasure mPathMeasure;

    private final boolean mAbsolute;

    /**
     * Constructor for building a PathAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * Note that for animating {@link Decor}, {@code absolute} will be ignored and always be true, meaning that the
     * Decor content View will only animate the translation X value given by the animation, instead of the combination
     * of the translation X value of the animation and ViewPager scrolling.
     *
     * @param page   Page object with specific page information about this animation.
     * @param path     Path object that the animated View will follow.
     * @param absolute Flag to set whether this animation should be relative to the scrolling page or not. If set
     *                 to true, the View being animated will ignore the scrolling of the parent View.
     */
    public PathAnimation(Page page, Path path, boolean absolute) {
        super(page);
        mPathMeasure = new PathMeasure(path, false);
        mAbsolute = absolute;
    }

    @Override
    public void onAnimate(View v, float offset, float offsetInPixel) {
        if (!mAbsolute) {
            offsetInPixel = 0;
        }
        offset = Math.abs(offset);

        float[] coordinates = new float[2];
        mPathMeasure.getPosTan(mPathMeasure.getLength() * offset, coordinates, null);
        v.setTranslationX(coordinates[0] + offsetInPixel);
        v.setTranslationY(coordinates[1]);
    }
}
