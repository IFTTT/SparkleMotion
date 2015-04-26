package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.AlphaAnimation;
import com.ifttt.jazzhands.animations.Animation;

/**
 * Demo Activity for {@link AlphaAnimation}.
 */
public class AlphaViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager viewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        AlphaAnimation alphaAnimation = new AlphaAnimation(Animation.ALL_PAGES, 1f, 0f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());

        JazzHands.with(viewPager)
                .animate(alphaAnimation)
                .on(R.id.pic_img_view);
    }


}
