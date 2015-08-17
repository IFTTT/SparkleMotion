package com.ifttt.jazzhands;

import android.support.v4.util.SimpleArrayMap;
import android.view.View;
import java.util.ArrayList;
import java.util.Collections;

/**
 * JazzHands animation driver, used to store all {@link Animation} assigned to it. ViewPager or
 * {@link JazzHandsViewPagerLayout} then uses {@link #presentAnimations(View, float, float)} with a
 * {@link android.support.v4.view.ViewPager.PageTransformer} to run
 * any animation that should be run at any given circumstance (e.g. current page).
 */
final class JazzHandsAnimationPresenter {
    /**
     * Current page from ViewPager, used to decide whether an animation should run.
     */
    private int mCurrentPage;

    /**
     * A SimpleArrayMap that saves all animations with the target View's ID as key.
     */
    private SimpleArrayMap<Integer, ArrayList<Animation>> mAnimations;

    /**
     * A SimpleArrayMap that saves all animations with the target
     * {@link Decor} as key.
     */
    private SimpleArrayMap<Decor, ArrayList<Animation>> mDecorAnimations;

    public JazzHandsAnimationPresenter() {
        mAnimations = new SimpleArrayMap<Integer, ArrayList<Animation>>(3);
        mDecorAnimations = new SimpleArrayMap<Decor, ArrayList<Animation>>(3);
    }

    /**
     * Add animations to the target View. The View's id is used as key.
     *
     * @param id Id of the target View.
     * @param animations Animations to be associated to this View.
     */
    public void addAnimation(int id, Animation... animations) {
        if (mAnimations.get(id) == null) {
            mAnimations.put(id, new ArrayList<Animation>(animations.length));
        }

        ArrayList<Animation> anims = mAnimations.get(id);
        Collections.addAll(anims, animations);
    }

    /**
     * Add animations to the target {@link Decor}.
     *
     * @param decor Target Decor.
     * @param animations Animations to be associated to this Decor.
     */
    public void addAnimation(Decor decor, Animation... animations) {
        if (mDecorAnimations.get(decor) == null) {
            mDecorAnimations.put(decor, new ArrayList<Animation>(animations.length));
        }

        ArrayList<Animation> anims = mDecorAnimations.get(decor);
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
        int animMapSize = mAnimations.size();

        // Animate all in-page animations.
        for (int i = 0; i < animMapSize; i++) {
            int key = mAnimations.keyAt(i);
            ArrayList<Animation> animations = mAnimations.get(key);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
                Animation animation = animations.get(j);

                final View viewToAnimate;

                if (key == parent.getId() || key == Animation.ANIMATION_ID_PAGE) {
                    viewToAnimate = parent;
                } else {
                    viewToAnimate = parent.findViewById(key);
                }

                if (animation == null || viewToAnimate == null || !animation.shouldAnimate(
                        mCurrentPage)) {
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
        int animMapSize = mDecorAnimations.size();
        for (int i = 0; i < animMapSize; i++) {
            Decor decor = mDecorAnimations.keyAt(i);
            ArrayList<Animation> animations = mDecorAnimations.get(decor);

            int animListSize = animations.size();
            for (int j = 0; j < animListSize; j++) {
                Animation animation = animations.get(j);
                if (animation == null || decor.contentView.getParent() == null
                        || position > decor.endPage || position < decor.startPage
                        || !animation.shouldAnimate(mCurrentPage)) {
                    continue;
                }

                animation.animate(decor.contentView, offset, 0);
            }
        }
    }

    void setCurrentPage(int currentPage) {
        this.mCurrentPage = currentPage;
    }
}
