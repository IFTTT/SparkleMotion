package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.ScaleAnimation;

/**
 * Created by zhelu on 3/27/15.
 */
public class ScaleViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager viewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ScaleAnimation scaleAnimation = new ScaleAnimation(1, 2, 1f, 0.3f);

        JazzHands.with(viewPager)
                .animate(scaleAnimation)
                .on(R.id.second_page_icon);

        ScaleAnimation scaleAnimation1 = new ScaleAnimation(0, 1, 1f, 0f);
        JazzHands.with(viewPager)
                .animate(scaleAnimation1)
                .on(R.id.icon, R.id.icon_1, R.id.icon_2, R.id.icon_3, R.id.icon_4);
    }
}
