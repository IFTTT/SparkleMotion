package com.ifttt.jazzhandsdemo;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.Animation;
import com.ifttt.jazzhands.animations.ParallaxAnimation;

import android.app.Activity;
import android.os.Bundle;

/**
 * Demo activity for {@link ParallaxAnimation}.
 */
public class ParallaxViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPagerLayout viewPagerLayout = (JazzHandsViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        ParallaxAnimation parallaxAnimation = new ParallaxAnimation(Animation.ALL_PAGES, 2f);

        JazzHands.with(viewPagerLayout)
                .animate(parallaxAnimation)
                .on(R.id.pic_img_view);
    }
}
