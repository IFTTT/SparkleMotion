package com.ifttt.sparklemotion;

import android.support.v4.view.ViewPager;

import java.util.ArrayList;
import java.util.Collections;

public final class ViewPagerAnimBuilder implements AnimBuilder<Integer> {

    /**
     * Animations to be used for a set of target Views. Will be cleared after calling
     * {@link #on(Integer)}.
     */
    final ArrayList<Animation> animations;

    final SparkleAnimationPresenter presenter;

    ViewPagerAnimBuilder(ViewPager viewPager) {
        if (viewPager == null) {
            throw new NullPointerException("ViewPager cannot be null");
        }

        animations = new ArrayList<>();
        presenter = SparkleMotionCompat.installAnimationPresenter(viewPager);
    }

    /**
     * Assign animations to SparkleMotion, which will then associate the animations to target Views.
     *
     * @param animations Animations to run.
     * @return this instance to chain functions.
     */
    @Override
    public AnimBuilder<Integer> animate(Animation... animations) {
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
    @Override
    public void on(Integer id) {
        Animation[] anims = new Animation[animations.size()];
        animations.toArray(anims);
        presenter.addAnimation(id, anims);

        animations.clear();
    }
}
