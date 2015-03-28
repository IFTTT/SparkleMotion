package com.ifttt.jazzhands.animations;

import android.view.View;
import android.view.ViewGroup;

import com.ifttt.jazzhands.R;

/**
 * Subclass of {@link Animation} that changes the View's translation x and y. 
 */
public class TranslationAnimation extends Animation {

    private float mTranslationX;
    private float mTranslationY;

    public TranslationAnimation(int start, int end, boolean absolute,
                                float translationX,
                                float translationY) {
        super(start, end, absolute);
        this.mTranslationX = translationX;
        this.mTranslationY = translationY;
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

                try {
                    parent = (ViewGroup) parent.getParent();
                } catch (ClassCastException e) {
                    parent = null;
                }
            }

            if (parent != null) {
                parent.setClipToPadding(false);
                parent.setClipChildren(false);
            }
        }

        if (!absolute) {
            offset = 0;
        }

        v.setTranslationX(fraction * mTranslationX + offset);
        v.setTranslationY(fraction * mTranslationY);
    }
}
