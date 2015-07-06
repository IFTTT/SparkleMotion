package com.ifttt.jazzhands.animations;

import android.support.v4.util.SimpleArrayMap;
import android.view.View;

import java.util.ArrayList;

/**
 * JazzHands animation driver, used to store all {@link Animation} assigned to it. {@link
 * com.ifttt.jazzhands.JazzHandsViewPager}
 * then uses {@link #presentAnimations(View, float, float)} with a
 * {@link android.support.v4.view.ViewPager.PageTransformer} to run
 * any animation that should be run at any given circumstance (e.g. current page).
 */
public class JazzHandsAnimationPresenter {
    /**
     * Current page from ViewPager, used to decide whether an animation should run.
     */
    private int mCurrentPage;

    /**
     * A HashMap that saves all animations with the target View's ID as key.
     */
    private SimpleArrayMap<Integer, ArrayList<Animation>> mAnimations;

    /**
     * Used only for cross page animation. Stores the largest pages that the animations in
     * this instance should travel. This is used to determine the offscreen page limit of
     * ViewPager.
     */
    private int mMaxPageDistance = -1;

    private int mViewPagerId;

    public JazzHandsAnimationPresenter() {
        mAnimations = new SimpleArrayMap<Integer, ArrayList<Animation>>();
    }

    public void addAnimation(int id, Animation... animations) {
        if (mAnimations.get(id) == null) {
            mAnimations.put(id, new ArrayList<Animation>());
        }

        ArrayList<Animation> infos = mAnimations.get(id);
        for (Animation animation : animations) {
            if (animation instanceof TranslationAnimation
                    || animation instanceof PathAnimation) {
                int distance = animation.pageEnd - animation.pageStart;
                if (distance > mMaxPageDistance) {
                    mMaxPageDistance = distance;
                }
            }

            infos.add(animation);
        }
    }

    public void presentAnimations(View parent, float fraction, float xOffset) {
        int animMapSize = mAnimations.size();
        for (int i = 0 ; i < animMapSize ; i++) {
            int key = mAnimations.keyAt(i);
            ArrayList<Animation> animations = mAnimations.get(key);

            int animListSize = animations.size();
            for (int j = 0 ; j < animListSize ; j++) {
                Animation animation = animations.get(j);

                final View viewToAnimate;

                if (key == parent.getId()) {
                    viewToAnimate = parent;
                } else {
                    viewToAnimate = parent.findViewById(key);
                }

                if (animation == null
                        || viewToAnimate == null
                        || !animation.shouldAnimate(mCurrentPage)) {
                    continue;
                }

                // Pass ViewPager's ID to animation for setClipChildren and setClipToPadding
                // in child Views.
                animation.setViewPagerId(mViewPagerId);
                animation.animate(viewToAnimate, fraction, xOffset);
            }
        }
    }

    public void setViewPagerId(int viewPagerId) {
        mViewPagerId = viewPagerId;
    }

    public void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }

    public int getMaxCrossPageAnimationPages() {
        return mMaxPageDistance;
    }

    public boolean hasCrossPageAnimation() {
        return mMaxPageDistance > 0;
    }
}
