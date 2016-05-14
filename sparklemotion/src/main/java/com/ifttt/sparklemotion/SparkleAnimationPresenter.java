package com.ifttt.sparklemotion;

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
    private SimpleArrayMap<Integer, ArrayList<Animation>> animations;

    /**
     * A SimpleArrayMap that saves all animations with the target
     * {@link Decor} as key.
     */
    private SimpleArrayMap<Decor, ArrayList<Animation>> decorAnimations;

    /**
     * An ArrayList that saves the ids of the Views being animated with Sparkle Motion.
     */
    private ArrayList<Integer> animatedViews;

    public SparkleAnimationPresenter() {
        animations = new SimpleArrayMap<>(3);
        decorAnimations = new SimpleArrayMap<>(3);
        animatedViews = new ArrayList<>(3);
    }

    /**
     * Add animations to the target View. The View's id is used as key.
     *
     * @param id Id of the target View.
     * @param animations Animations to be associated to this View.
     */
    public void addAnimation(int id, Animation... animations) {
        if (this.animations.get(id) == null) {
            this.animations.put(id, new ArrayList<Animation>(animations.length));
            if (id != Animation.FULL_PAGE) {
                animatedViews.add(id);
            }
        }

        ArrayList<Animation> anims = this.animations.get(id);
        Collections.addAll(anims, animations);
    }

    /**
     * Add animations to the target {@link Decor}.
     *
     * @param decor Target Decor.
     * @param animations Animations to be associated to this Decor.
     */
    public void addAnimation(Decor decor, Animation... animations) {
        if (decorAnimations.get(decor) == null) {
            decorAnimations.put(decor, new ArrayList<Animation>(animations.length));
        }

        ArrayList<Animation> anims = decorAnimations.get(decor);
        if (anims != null) {
            Collections.addAll(anims, animations);
        }
    }

    /**
     * Run the animations based on the View animations saved within the presenter and the offset of
     * the scrolling.
     *
     * @param parent Current page View of the ViewPager.
     * @param offset Scrolling offset of the ViewPager.
     * @param offsetInPixel Scrolling offset in pixels based on the page View.
     */
    void presentAnimations(View parent, float offset, float offsetInPixel) {
        int animMapSize = animations.size();

        // Animate all in-page animations.
        for (int i = 0; i < animMapSize; i++) {
            int key = animations.keyAt(i);
            ArrayList<Animation> animations = this.animations.get(key);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
                Animation animation = animations.get(j);

                final View viewToAnimate;

                if (key == parent.getId() || key == Animation.FULL_PAGE) {
                    viewToAnimate = parent;
                } else {
                    viewToAnimate = parent.findViewById(key);
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
     * @param offset Offset of the ViewPager scrolling.
     */
    void presentDecorAnimations(int position, float offset) {
        // Animate all decor or other View animations.
        int animMapSize = decorAnimations.size();
        for (int i = 0; i < animMapSize; i++) {
            Decor decor = decorAnimations.keyAt(i);
            ArrayList<Animation> animations = decorAnimations.get(decor);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
                Animation animation = animations.get(j);
                if (animation == null) {
                    continue;
                }

                if (!animation.shouldAnimate(position)) {
                    continue;
                }

                animation.animate(decor.contentView, offset, 0);
            }
        }
    }

    /**
     * @return A List of ids that Sparkle Motion animates within the ViewPager.
     */
    List<Integer> getAnimatedViews() {
        return animatedViews;
    }
}
