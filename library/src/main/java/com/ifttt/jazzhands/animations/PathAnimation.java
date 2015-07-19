package com.ifttt.jazzhands.animations;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

import com.ifttt.jazzhands.Animation;

/**
 * Subclass of {@link Animation} that animates the view based on a {@link Path}. It is essentially a translation
 * animation, but the translation x and y is changed to follow the path.
 */
public class PathAnimation extends Animation {

    private PathMeasure mPathMeasure;

    public PathAnimation(int page, boolean absolute, Path path) {
        this(page, page, absolute, path);
    }

    public PathAnimation(int start, int end, boolean absolute, Path path) {
        super(start, end, absolute);
        mPathMeasure = new PathMeasure(path, false);
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        if (!absolute) {
            offset = 0;
        }
        fraction = Math.abs(fraction);

        float[] coordinates = new float[2];
        mPathMeasure.getPosTan(mPathMeasure.getLength() * fraction, coordinates, null);
        v.setTranslationX(coordinates[0] + offset);
        v.setTranslationY(coordinates[1]);
    }
}
