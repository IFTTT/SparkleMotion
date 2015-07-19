package com.ifttt.jazzhands;

import com.ifttt.jazzhands.animations.PathAnimation;
import com.ifttt.jazzhands.animations.TranslationAnimation;

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

    private SimpleArrayMap<JazzHandsViewPagerLayout.Decor, ArrayList<Animation>> mDecorAnimations;

    /**
     * Used only for cross page animation. Stores the largest pages that the animations in
     * this instance should travel. This is used to determine the offscreen page limit of
     * ViewPager.
     */
    private int mMaxPageDistance = -1;

    public JazzHandsAnimationPresenter() {
        mAnimations = new SimpleArrayMap<Integer, ArrayList<Animation>>(3);
        mDecorAnimations = new SimpleArrayMap<JazzHandsViewPagerLayout.Decor, ArrayList<Animation>>(3);
    }

    public void addAnimation(int id, Animation... animations) {
        if (mAnimations.get(id) == null) {
            mAnimations.put(id, new ArrayList<Animation>());
        }

        ArrayList<Animation> anims = mAnimations.get(id);
        for (Animation animation : animations) {
            if (animation instanceof TranslationAnimation
                    || animation instanceof PathAnimation) {
                int distance = animation.pageEnd - animation.pageStart;
                if (distance > mMaxPageDistance) {
                    mMaxPageDistance = distance;
                }
            }

            anims.add(animation);
        }
    }

    public void addAnimation(JazzHandsViewPagerLayout.Decor decor, Animation... animations) {
        if (mDecorAnimations.get(decor) == null) {
            mDecorAnimations.put(decor, new ArrayList<Animation>());
        }

        ArrayList<Animation> anims = mDecorAnimations.get(decor);
        if (anims != null) {
            for (Animation animation : animations) {
                if (animation instanceof TranslationAnimation
                        || animation instanceof PathAnimation) {
                    int distance = animation.pageEnd - animation.pageStart;
                    if (distance > mMaxPageDistance) {
                        mMaxPageDistance = distance;
                    }
                }

                anims.add(animation);
            }
        }
    }

    void presentAnimations(View parent, float fraction, float xOffset) {
        int animMapSize = mAnimations.size();

        // Animate all in-page animations.
        for (int i = 0; i < animMapSize; i++) {
            int key = mAnimations.keyAt(i);
            ArrayList<Animation> animations = mAnimations.get(key);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
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

                animation.animate(viewToAnimate, fraction, xOffset);
            }
        }
    }

    void presentDecorAnimations(float position, float fraction) {
        // Animate all decor or other View animations.
        int animMapSize = mDecorAnimations.size();
        for (int i = 0; i < animMapSize; i++) {
            JazzHandsViewPagerLayout.Decor decor = mDecorAnimations.keyAt(i);
            ArrayList<Animation> animations = mDecorAnimations.get(decor);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
                Animation animation = animations.get(j);
                if (animation == null
                        || decor.contentView == null
                        || decor.contentView.getParent() == null
                        || position > decor.endPage
                        || position < decor.startPage
                        || !animation.shouldAnimate(mCurrentPage)) {
                    continue;
                }

                animation.animate(decor.contentView, fraction, 0);
            }
        }
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
