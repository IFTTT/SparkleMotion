package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Top level Builder class. Used for constructing a {@link SparkleAnimationPresenter} and
 * associate it with {@link ViewPager}.
 */
public class SparkleMotion {

    private final ViewPager viewPager;
    private SparkleViewPagerLayout viewPagerLayout;
    private SparkleAnimationPresenter presenter;

    /**
     * Animations to be used for a set of target Views. Will be cleared after calling
     * {@link #on(int...)}.
     */
    private ArrayList<Animation> animations;

    /**
     * Start constructing a {@link SparkleMotion} builder with a {@link ViewPager} instance. Animations
     * assigned
     * to this builder will be assigned to the ViewPager.
     *
     * @param viewPager Target ViewPager.
     * @return this instance to chain functions.
     */
    public static SparkleMotion with(@NonNull ViewPager viewPager) {
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
    public static SparkleMotion with(@NonNull SparkleViewPagerLayout viewPagerLayout) {
        return new SparkleMotion(viewPagerLayout);
    }

    /**
     * Private constructor for initializing the instance with ViewPager {@link ViewPager}
     * and {@link SparkleAnimationPresenter} reference.
     *
     * @param viewPager ViewPager object.
     */
    private SparkleMotion(@NonNull ViewPager viewPager) {
        this.viewPager = viewPager;
        init();
    }

    /**
     * Private constructor for initializing the instance with {@link SparkleViewPagerLayout}
     * and {@link SparkleAnimationPresenter} reference.
     *
     * @param viewPagerLayout ViewPagerLayout object.
     */
    private SparkleMotion(@NonNull SparkleViewPagerLayout viewPagerLayout) {
        this.viewPagerLayout = viewPagerLayout;
        viewPager = this.viewPagerLayout.getViewPager();

        init();
    }

    private void init() {
        if (SparkleMotionCompat.hasPresenter(viewPager)) {
            presenter = SparkleMotionCompat.getAnimationPresenter(viewPager);
        } else {
            presenter = new SparkleAnimationPresenter();
        }

        animations = new ArrayList<Animation>();
    }

    /**
     * Assign animations to SparkleMotion, which will then associate the animations to target Views.
     *
     * @param animations Animations to run.
     * @return this instance to chain functions.
     */
    public SparkleMotion animate(Animation... animations) {
        Collections.addAll(this.animations, animations);
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
        if (viewPagerLayout == null) {
            throw new IllegalStateException("A ViewPagerLayout must be provided for animating Decor");
        }

        Animation[] animations = new Animation[this.animations.size()];
        this.animations.toArray(animations);

        for (Decor decor : decors) {
            presenter.addAnimation(decor, animations);
        }

        this.animations.clear();

        ViewPager viewPager = viewPagerLayout.getViewPager();
        if (viewPager == null) {
            throw new NullPointerException("ViewPager cannot be null");
        }

        SparkleMotionCompat.installAnimationPresenter(viewPager, false, presenter);

        for (Decor decor : decors) {
            viewPagerLayout.addDecor(decor);
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
        Animation[] anims = new Animation[animations.size()];
        animations.toArray(anims);

        for (int id : ids) {
            presenter.addAnimation(id, anims);
        }

        animations.clear();

        ViewPager viewPager;
        if (viewPagerLayout != null && this.viewPager == null) {
            viewPager = viewPagerLayout.getViewPager();
        } else {
            viewPager = this.viewPager;
        }

        if (viewPager == null) {
            throw new NullPointerException("ViewPager cannot be null");
        }

        SparkleMotionCompat.installAnimationPresenter(viewPager, false, presenter);
    }
}
