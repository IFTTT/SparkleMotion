package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;

public class ViewPagerLayoutAnimBuilder extends ViewPagerAnimBuilder {

    private final SparkleViewPagerLayout mViewPagerLayout;

    ViewPagerLayoutAnimBuilder(@NonNull SparkleViewPagerLayout viewPagerLayout) {
        super(viewPagerLayout.getViewPager());
        mViewPagerLayout = viewPagerLayout;
    }

    @Override
    public void on(Object target) {
        super.on(target);

        if (target instanceof Decor) {
            ViewPager viewPager = mViewPagerLayout.getViewPager();
            if (viewPager == null) {
                throw new NullPointerException("ViewPager cannot be null");
            }

            mViewPagerLayout.addDecor((Decor) target);
        }
    }
}
