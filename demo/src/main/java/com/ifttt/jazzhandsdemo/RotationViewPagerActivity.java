package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.Animation;
import com.ifttt.jazzhands.animations.RotationAnimation;

/**
 * Demo Activity for {@link RotationAnimation}.
 */
public class RotationViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager viewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        RotationAnimation rotationAnimation =
                new RotationAnimation(Animation.ALL_PAGES, 360);
        rotationAnimation.setInterpolator(new AccelerateInterpolator());

        JazzHands.with(viewPager)
                .animate(rotationAnimation)
                .on(R.id.pic_img_view);
    }
}
