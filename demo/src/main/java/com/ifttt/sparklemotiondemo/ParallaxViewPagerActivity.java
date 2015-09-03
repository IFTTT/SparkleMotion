package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.animations.ParallaxAnimation;

/**
 * Demo activity for {@link ParallaxAnimation}.
 */
public class ParallaxViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ParallaxAnimation parallaxAnimation = new ParallaxAnimation(Animation.ALL_PAGES, 2f);

        SparkleMotion.with(viewPager) //
                .animate(parallaxAnimation) //
                .on(R.id.pic_img_view);
    }
}
