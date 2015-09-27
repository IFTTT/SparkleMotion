package com.ifttt.sparklemotion;

import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public abstract class ViewAnimationBuilder<T> {

    /**
     * Animations to be used for a set of target Views. Will be cleared after calling
     * {@link #on(Integer)}.
     */
    final ArrayList<Animation> animations;
    final SparkleAnimationPresenter presenter;

    ViewAnimationBuilder(T view) {
        animations = new ArrayList<>();
        presenter = getPresenter(view);
    }

    public abstract SparkleAnimationPresenter getPresenter(T container);

    /**
     * Assign animations to SparkleMotion, which will then associate the animations to target Views.
     *
     * @param animations Animations to run.
     * @return this instance to chain functions.
     */
    public ViewAnimationBuilder animate(Animation... animations) {
        Collections.addAll(this.animations, animations);
        return this;
    }

    /**
     * Assign target Views to SparkleMotion, which will assign the animations stored in {@link
     * #animate(Animation...)} to {@link SparkleAnimationPresenter}. This is the last method to
     * call in order to build a functional ViewPager. Once this is called, a
     * {@link SparkleAnimationPresenter} will be associated to the ViewPager, and the animations
     * will be run when scrolling.
     *
     * @param id Target View id.
     */
    public void on(Integer id) {
        Animation[] anims = new Animation[animations.size()];
        animations.toArray(anims);
        presenter.addAnimation(id, anims);

        animations.clear();
    }
}
