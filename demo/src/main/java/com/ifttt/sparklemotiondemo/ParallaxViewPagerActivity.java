package com.ifttt.sparklemotiondemo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.animations.ParallaxAnimation;

/**
 * Demo activity for {@link ParallaxAnimation}.
 */
public final class ParallaxViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ParallaxAnimation parallaxAnimation = new ParallaxAnimation(2f);

        SparkleMotion.with(viewPager) //
                .animate(parallaxAnimation) //
                .on(R.id.pic_img_view);
    }
}
