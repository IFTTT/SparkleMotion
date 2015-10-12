package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

/**
 * Top level Builder class. Used for constructing a {@link SparkleAnimationPresenter} and
 * associate it with {@link ViewPager}.
 */
public class SparkleMotion {

    /**
     * Start constructing a {@link ViewPagerAnimBuilder} with a {@link ViewPager} instance. Animations
     * assigned to this builder will be controlled by the ViewPager.
     *
     * @param viewPager Target ViewPager.
     * @return ViewPagerAnimBuilder instance.
     */
    public static ViewPagerAnimBuilder with(@NonNull ViewPager viewPager) {
        return new ViewPagerAnimBuilder(viewPager);
    }

    /**
     * Start constructing a {@link ViewPagerLayoutAnimBuilder} with a {@link SparkleViewPagerLayout}
     * instance. Animations assigned to this builder will be controlled by the layout.
     *
     * @param viewPagerLayout Target ViewPagerLayout.
     * @return ViewPagerLayoutAnimBuilder instance.
     */
    public static ViewPagerLayoutAnimBuilder with(@NonNull SparkleViewPagerLayout viewPagerLayout) {
        return new ViewPagerLayoutAnimBuilder(viewPagerLayout);
    }

    /**
     * Start constructing a {@link CoordinatorAnimBuilder} with a {@link SparkleAnimationBehavior} instance.
     * Animations assigned to this builder will be controlled by the CoordinatorLayout.
     *
     * @param behavior Target SparkleAnimationBehavior.
     * @return CoordinatorAnimBuilder instance.
     */
    public static CoordinatorAnimBuilder with(@NonNull SparkleAnimationBehavior behavior) {
        return new CoordinatorAnimBuilder(behavior);
    }

}
