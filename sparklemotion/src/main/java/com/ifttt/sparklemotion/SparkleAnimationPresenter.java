package com.ifttt.sparklemotion;

import android.support.design.widget.CoordinatorLayout;
import android.support.v4.util.SimpleArrayMap;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Animation driver, used to store all {@link Animation} assigned to it and run animations given the current
 * circumstance (e.g current page, View visibility). For ViewPager animations,
 * {@link #presentAnimations(View, float, float)} will be called for every frame the child View is scrolled. For
 * Decor animations, {@link #presentDecorAnimations(int, float)} will be called for every frame the ViewPager is
 * scrolled.
 */
final class SparkleAnimationPresenter {
    /**
     * A SimpleArrayMap that saves all animations with the target View's ID as key.
     */
    private final SimpleArrayMap<Object, ArrayList<Animation>> mObjectAnimations;

    /**
     * An ArrayList that saves the ids of the Views being animated with Sparkle Motion.
     */
    private ArrayList<Integer> mAnimatedViews;

    /**
     * Stored previous frame's ViewPager position to determine whether we should animate one more frame when
     * the ViewPager scroll across pages.
     */
    private int mPreviousPosition;

    public SparkleAnimationPresenter() {
        mAnimatedViews = new ArrayList<>(3);
        mObjectAnimations = new SimpleArrayMap<>(3);
    }

    /**
     * Add animations to the target View. The View's id is used as key.
     *
     * @param target     Id of the target View.
     * @param animations Animations to be associated to this View.
     */
    public void addAnimation(Object target, Animation... animations) {
        if (mObjectAnimations.get(target) == null) {
            mObjectAnimations.put(target, new ArrayList<Animation>(animations.length));
            if (target != Animation.FULL_PAGE && target instanceof Integer) {
                mAnimatedViews.add((Integer) target);
            }
        }

        ArrayList<Animation> anims = mObjectAnimations.get(target);
        Collections.addAll(anims, animations);
    }

    /**
     * Run the animations based on the View animations saved within the presenter and the offset of
     * the scrolling.
     *
     * @param parent        Current page View of the ViewPager.
     * @param offset        Scrolling offset of the ViewPager.
     * @param offsetInPixel Scrolling offset in pixels based on the page View.
     */
    void presentAnimations(View parent, float offset, float offsetInPixel) {
        int animMapSize = mObjectAnimations.size();

        // Animate all in-page animations.
        for (int i = 0; i < animMapSize; i++) {
            Object key = mObjectAnimations.keyAt(i);
            if (!(key instanceof Integer)) {
                continue;
            }
            int id = (int) key;
            ArrayList<Animation> animations = mObjectAnimations.get(id);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
                Animation animation = animations.get(j);

                final View viewToAnimate;

                if (id == parent.getId() || id == Animation.FULL_PAGE) {
                    viewToAnimate = parent;
                } else {
                    viewToAnimate = parent.findViewById(id);
                }

                if (animation == null || viewToAnimate == null) {
                    continue;
                }

                animation.animate(viewToAnimate, offset, offsetInPixel);
            }
        }
    }

    /**
     * Run the animations based on the Decor animations saved within the presenter and the offset
     * of the scrolling.
     *
     * @param position Position of the current page.
     * @param offset   Offset of the ViewPager scrolling.
     */
    void presentDecorAnimations(int position, float offset) {
        // Animate all decor or other View animations.
        int animMapSize = mObjectAnimations.size();
        for (int i = 0; i < animMapSize; i++) {
            Object key = mObjectAnimations.keyAt(i);
            if (!(key instanceof Decor)) {
                continue;
            }

            Decor decor = (Decor) key;
            ArrayList<Animation> animations = mObjectAnimations.get(decor);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
                Animation animation = animations.get(j);
                if (animation == null) {
                    continue;
                }

                if (!animation.shouldAnimate(position)) {
                    // Add a rescue frame to the animation if the page is scrolled really fast.
                    if (mPreviousPosition < position && animation.pageEnd < position) {
                        animation.animate(decor.contentView, 1, 0);
                    } else if (mPreviousPosition > position && animation.pageStart > position) {
                        animation.animate(decor.contentView, 0, 0);
                    }

                    continue;
                }

                animation.animate(decor.contentView, offset, 0);
            }
        }

        mPreviousPosition = position;
    }

    void presentViewAnimations(float progress) {
        int animMapSize = mObjectAnimations.size();
        for (int i = 0; i < animMapSize; i++) {
            Object key = mObjectAnimations.keyAt(i);
            if (!(key instanceof View) ||
                    !(((View) key).getLayoutParams() instanceof CoordinatorLayout.LayoutParams)) {
                continue;
            }

            View view = (View) key;

            ArrayList<Animation> animations = mObjectAnimations.get(key);
            int animsSize = animations.size();
            for (int j = 0; j < animsSize; j++) {
                Animation animation = animations.get(j);
                animation.animate(view, progress, 0);
            }
        }
    }

    /**
     * @return A List of ids that Sparkle Motion animates within the ViewPager.
     */
    List<Integer> getAnimatedViews() {
        return mAnimatedViews;
    }
}
