package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

public final class ViewPagerLayoutAnimBuilder extends ViewPagerAnimBuilder {

    private final SparkleViewPagerLayout mViewPagerLayout;

    ViewPagerLayoutAnimBuilder(@NonNull SparkleViewPagerLayout viewPagerLayout) {
        super(viewPagerLayout.getViewPager());
        mViewPagerLayout = viewPagerLayout;
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
     * @param decor Target Decors.
     * @throws IllegalStateException when a ViewPagerLayout is not provided.
     */
    public void on(Decor decor) {
        Animation[] animationsArray = new Animation[animations.size()];
        animations.toArray(animationsArray);
        animations.clear();

        presenter.addAnimation(decor, animationsArray);

        ViewPager viewPager = mViewPagerLayout.getViewPager();
        if (viewPager == null) {
            throw new NullPointerException("ViewPager cannot be null");
        }

        mViewPagerLayout.addDecor(decor);
    }
}
