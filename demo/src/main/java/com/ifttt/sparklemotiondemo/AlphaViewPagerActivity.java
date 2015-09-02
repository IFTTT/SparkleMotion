package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.AlphaAnimation;

/**
 * Demo Activity for {@link AlphaAnimation}.
 */
public class AlphaViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager =
                (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        AlphaAnimation alphaAnimation = new AlphaAnimation(Animation.ALL_PAGES, 0f, 1f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());

        SparkleMotion.with(viewPager) //
                .animate(alphaAnimation) //
                .on(R.id.pic_img_view);
    }
}