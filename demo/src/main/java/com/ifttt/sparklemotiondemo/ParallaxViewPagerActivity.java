package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.ParallaxAnimation;

/**
 * Demo activity for {@link ParallaxAnimation}.
 */
public class ParallaxViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        SparkleViewPagerLayout viewPagerLayout =
                (SparkleViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        ParallaxAnimation parallaxAnimation = new ParallaxAnimation(Animation.ALL_PAGES, 2f);

        SparkleMotion.with(viewPagerLayout) //
                .animate(parallaxAnimation) //
                .on(R.id.pic_img_view);
    }
}
