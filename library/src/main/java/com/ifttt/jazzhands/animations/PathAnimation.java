package com.ifttt.jazzhands.animations;

import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;
import android.view.ViewGroup;

import com.ifttt.jazzhands.R;

/**
 * Subclass of {@link Animation} that animates the view based on a {@link Path}. It is essentially a translation
 * animation, but the translation x and y is changed to follow the path.
 */
public class PathAnimation extends Animation {

    private PathMeasure mPathMeasure;

    public PathAnimation(int start, int end, boolean absolute, Path path) {
        super(start, end, absolute);
        mPathMeasure = new PathMeasure(path, false);
    }

    @Override
    public void onAnimate(View v, float fraction, float offset) {
        ViewGroup parent = (ViewGroup) v.getParent();
        if (absolute) {
            // If the animation should be run based on the screen, set the parent and ancestors to not clip to
            // padding or clip children.
            while (parent != null
                    && !PAGE_ROOT_FLAG.equals(parent.getTag(R.id.page_root_flag))) {
                parent.setClipToPadding(false);
                parent.setClipChildren(false);

                parent = (ViewGroup) parent.getParent();
            }

            if (parent != null) {
                parent.setClipToPadding(false);
                parent.setClipChildren(false);
            }
        }

        if (!absolute) {
            offset = 0;
        }

        float[] coordinates = new float[2];
        mPathMeasure.getPosTan(mPathMeasure.getLength() * fraction, coordinates, null);
        v.setTranslationX(coordinates[0] + offset);
        v.setTranslationY(coordinates[1]);
    }
}
