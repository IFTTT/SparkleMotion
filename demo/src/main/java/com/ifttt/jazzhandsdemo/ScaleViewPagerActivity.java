package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.ScaleAnimation;

/**
 * Demo Activity for {@link ScaleAnimation}.
 */
public class ScaleViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager viewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1f, 0.3f);

        JazzHands.with(viewPager)
                .animate(scaleAnimation)
                .on(R.id.sunrise_img, R.id.sunset_img);
    }
}
