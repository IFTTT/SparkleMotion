package com.ifttt.sparklemotion;

import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Top level Builder class. Used for constructing a {@link SparkleAnimationPresenter} and
 * associate it with {@link ViewPager}.
 */
public class SparkleMotion {

    private ViewPager mViewPager;
    private SparkleViewPagerLayout mViewPagerLayout;
    private SparkleAnimationPresenter mPresenter;

    private boolean mReversedOrder;

    /**
     * Animations to be used for a set of target Views. Will be cleared after calling
     * {@link #on(int...)}.
     */
    private ArrayList<Animation> mAnimations;

    /**
     * Start constructing a {@link SparkleMotion} builder with a {@link ViewPager} instance. Animations
     * assigned
     * to this builder will be assigned to the ViewPager.
     *
     * @param viewPager Target ViewPager.
     * @return this instance to chain functions.
     */
    public static SparkleMotion with(ViewPager viewPager) {
        return new SparkleMotion(viewPager);
    }

    /**
     * Start constructing a {@link SparkleMotion} builder with a {@link SparkleViewPagerLayout}
     * instance. Animations
     * assigned to this builder will be assigned to the ViewPager.
     *
     * @param viewPagerLayout TargetViewPagerLayout.
     * @return this instance to chain functions.
     */
    public static SparkleMotion with(SparkleViewPagerLayout viewPagerLayout) {
        return new SparkleMotion(viewPagerLayout);
    }

    /**
     * Private constructor for initializing the instance with ViewPager {@link ViewPager}
     * and {@link SparkleAnimationPresenter} reference.
     *
     * @param viewPager ViewPager object.
     */
    private SparkleMotion(ViewPager viewPager) {
        mViewPager = viewPager;
        init();
    }

    /**
     * Private constructor for initializing the instance with {@link SparkleViewPagerLayout}
     * and {@link SparkleAnimationPresenter} reference.
     *
     * @param viewPagerLayout ViewPagerLayout object.
     */
    private SparkleMotion(SparkleViewPagerLayout viewPagerLayout) {
        mViewPagerLayout = viewPagerLayout;

        init();
    }

    private void init() {
        if (SparkleMotionCompat.hasPresenter(mViewPager)) {
            mPresenter = SparkleMotionCompat.getAnimationPresenter(mViewPager);
        } else {
            mPresenter = new SparkleAnimationPresenter();
        }

        mAnimations = new ArrayList<Animation>();
    }

    /**
     * Set child drawing order to reversed for the ViewPager. Default value is false.
     *
     * @return this instance to chain functions.
     */
    public SparkleMotion reverseDrawingOrder() {
        mReversedOrder = true;
        return this;
    }

    /**
     * Assign animations to SparkleMotion, which will then associate the animations to target Views.
     *
     * @param animations Animations to run.
     * @return this instance to chain functions.
     */
    public SparkleMotion animate(Animation... animations) {
        Collections.addAll(mAnimations, animations);
        return this;
    }

    /**
     * Assign target {@link Decor} to SparkleMotion, which will assign the
     * animations stored in {@link #animate(Animation...)} to {@link SparkleAnimationPresenter}.
     * This is the last
     * method to call in order to build a functional ViewPager. Once this is called, a {@link
     * SparkleAnimationPresenter} will be associated to the ViewPager, and the animations will be
     * run when scrolling.
     * <p/>
     * Note that to use this method, a {@link SparkleViewPagerLayout} must be provided.
     *
     * @param decors Target Decors.
     * @throws IllegalStateException when a ViewPagerLayout is not provided.
     */
    public void on(final Decor... decors) {
        if (mViewPagerLayout == null) {
            throw new IllegalStateException("A ViewPagerLayout must be provided for animating Decor");
        }

        Animation[] animations = new Animation[mAnimations.size()];
        mAnimations.toArray(animations);

        for (Decor decor : decors) {
            mPresenter.addAnimation(decor, animations);
        }

        mAnimations.clear();

        ViewPager viewPager = mViewPagerLayout.getViewPager();
        if (viewPager == null) {
            throw new NullPointerException("ViewPager cannot be null");
        }

        SparkleMotionCompat.installAnimationPresenter(viewPager, mReversedOrder, mPresenter);

        for (Decor decor : decors) {
            mViewPagerLayout.addDecor(decor);
        }
    }

    /**
     * Assign target Views to SparkleMotion, which will assign the animations stored in {@link
     * #animate(Animation...)} to {@link SparkleAnimationPresenter}. This is the last method to
     * call in order to build a functional ViewPager. Once this is called, a
     * {@link SparkleAnimationPresenter} will be associated to the ViewPager, and the animations
     * will be run when scrolling.
     *
     * @param ids Target View ids.
     */
    public void on(final int... ids) {
        Animation[] anims = new Animation[mAnimations.size()];
        mAnimations.toArray(anims);

        for (int id : ids) {
            mPresenter.addAnimation(id, anims);
        }

        mAnimations.clear();

        ViewPager viewPager;
        if (mViewPagerLayout != null && mViewPager == null) {
            viewPager = mViewPagerLayout.getViewPager();
        } else {
            viewPager = mViewPager;
        }

        if (viewPager == null) {
            throw new NullPointerException("ViewPager cannot be null");
        }

        SparkleMotionCompat.installAnimationPresenter(viewPager, mReversedOrder, mPresenter);
    }
}
