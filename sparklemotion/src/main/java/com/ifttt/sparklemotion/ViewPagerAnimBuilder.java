package com.ifttt.sparklemotion;

import android.support.v4.view.ViewPager;

public class ViewPagerAnimBuilder extends ViewAnimationBuilder<ViewPager> {

    ViewPagerAnimBuilder(ViewPager viewPager) {
        super(viewPager);

        if (viewPager == null) {
            throw new NullPointerException("ViewPager cannot be null");
        }
    }

    @Override
    public SparkleAnimationPresenter getPresenter(ViewPager viewPager) {
        return SparkleMotionCompat.installAnimationPresenter(viewPager);
    }
}
