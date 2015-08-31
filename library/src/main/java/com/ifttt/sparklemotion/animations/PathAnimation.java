package com.ifttt.sparklemotion.animations;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

import com.ifttt.sparklemotion.Animation;

/**
 * Subclass of {@link Animation} that animates the view based on a {@link Path}. It is essentially
 * a translation animation, but the translation x and y is changed to follow the path.
 */
public class PathAnimation extends Animation {

    private final PathMeasure mPathMeasure;

    private final boolean mAbsolute;

    /**
     * Constructor for building a PathAnimation for all pages. This should be used for ViewPager View animations,
     * as they will also be involved in ViewPager scrolling, therefore making them invisible once they are scrolled
     * to left or right.
     */
    public PathAnimation(Path path, boolean absolute) {
        this(ALL_PAGES, path, absolute);
    }

    /**
     * Constructor for building a PathAnimation for a specific page. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param page Page index that this animation should run on.
     */
    public PathAnimation(int page, Path path, boolean absolute) {
        this(page, page, path, absolute);
    }

    /**
     * Constructor for building a PathAnimation for a range of pages. This is recommended to use
     * for running {@link com.ifttt.sparklemotion.Decor} animations, as a Decor can exists in a range of pages, and
     * run different animations.
     *
     * @param start    Page index that this animation should start.
     * @param end      Page index that this animation should end.
     * @param path     Path object that the animated View will follow.
     * @param absolute Flag to set whether this animation should be relative to the scrolling page or not. If set
     *                 to true, the View being animated will ignore the scrolling of the parent View.
     */
    public PathAnimation(int start, int end, Path path, boolean absolute) {
        super(start, end);
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
