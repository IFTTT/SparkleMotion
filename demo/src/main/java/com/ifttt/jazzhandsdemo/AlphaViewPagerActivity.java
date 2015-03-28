package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.AlphaAnimation;

/**
 * Created by zhelu on 3/27/15.
 */
public class AlphaViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager viewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 2, 1f, 0f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());

        JazzHands.with(viewPager)
                .animate(alphaAnimation)
                .on(R.id.second_page_icon);
    }


}
