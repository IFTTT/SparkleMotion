package com.ifttt.jazzhands.animations;

import com.ifttt.jazzhands.Animation;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

/**
 * Subclass of {@link Animation} that animates the view based on a {@link Path}. It is essentially a translation
 * animation, but the translation x and y is changed to follow the path.
 */
public class PathAnimation extends Animation {

    private final PathMeasure mPathMeasure;

    /**
     * Flag to set whether this animation should be relative to the scrolling page or not. If set to true, the View
     * being animated will ignore the scrolling of the parent View.
     */
    private final boolean mAbsolute;

    public PathAnimation(int page, boolean absolute, Path path) {
        this(page, page, absolute, path);
    }

    public PathAnimation(int start, int end, boolean absolute, Path path) {
        super(start, end);
        mAbsolute = absolute;
        mPathMeasure = new PathMeasure(path, false);
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
