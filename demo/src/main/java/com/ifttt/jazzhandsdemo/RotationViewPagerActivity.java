package com.ifttt.jazzhandsdemo;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.Animation;
import com.ifttt.jazzhands.animations.RotationAnimation;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;

/**
 * Demo Activity for {@link RotationAnimation}.
 */
public class RotationViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPagerLayout viewPagerLayout = (JazzHandsViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        RotationAnimation rotationAnimation =
                new RotationAnimation(Animation.ALL_PAGES, 360);
        rotationAnimation.setInterpolator(new AccelerateInterpolator());

        JazzHands.with(viewPagerLayout)
                .animate(rotationAnimation)
                .on(R.id.pic_img_view);
    }
}
