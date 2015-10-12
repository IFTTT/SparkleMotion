package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import com.ifttt.sparklemotion.Page;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.animations.AlphaAnimation;

/**
 * Demo Activity for {@link AlphaAnimation}.
 */
public final class AlphaViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        AlphaAnimation alphaAnimation = new AlphaAnimation(Page.allPages(), 1f, 0f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());

        SparkleMotion.with(viewPager)
                .animate(alphaAnimation)
                .on(R.id.pic_img_view);
    }
}
