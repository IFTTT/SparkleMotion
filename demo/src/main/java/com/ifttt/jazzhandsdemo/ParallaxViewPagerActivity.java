package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.Animation;
import com.ifttt.jazzhands.animations.ParallaxAnimation;

/**
 * Demo activity for {@link ParallaxAnimation}.
 */
public class ParallaxViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager jazzHandsViewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        jazzHandsViewPager.setAdapter(new PagerAdapter());

        ParallaxAnimation parallaxAnimation = new ParallaxAnimation(Animation.ALL_PAGES, 2f);

        JazzHands.with(jazzHandsViewPager)
                .animate(parallaxAnimation)
                .on(R.id.pic_img_view);
    }
}
