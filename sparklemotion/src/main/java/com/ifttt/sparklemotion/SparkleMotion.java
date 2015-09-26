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

    /**
     * Start constructing a {@link SparkleMotion} builder with a {@link ViewPager} instance. Animations
     * assigned
     * to this builder will be assigned to the ViewPager.
     *
     * @param viewPager Target ViewPager.
     * @return this instance to chain functions.
     */
    public static ViewPagerAnimBuilder with(@NonNull ViewPager viewPager) {
        return new ViewPagerAnimBuilder(viewPager);
    }

    /**
     * Start constructing a {@link SparkleMotion} builder with a {@link SparkleViewPagerLayout}
     * instance. Animations
     * assigned to this builder will be assigned to the ViewPager.
     *
     * @param viewPagerLayout TargetViewPagerLayout.
     * @return this instance to chain functions.
     */
    public static ViewPagerLayoutAnimBuilder with(@NonNull SparkleViewPagerLayout viewPagerLayout) {
        return new ViewPagerLayoutAnimBuilder(viewPagerLayout);
    }

}
