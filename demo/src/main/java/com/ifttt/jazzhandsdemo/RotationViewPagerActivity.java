package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.animation.AccelerateInterpolator;
import com.ifttt.jazzhands.Animation;
import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.animations.RotationAnimation;

/**
 * Demo Activity for {@link RotationAnimation}.
 */
public class RotationViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager =
                (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        RotationAnimation rotationAnimation = new RotationAnimation(Animation.ALL_PAGES, 360);
        rotationAnimation.setInterpolator(new AccelerateInterpolator());

        JazzHands.with(viewPager) //
                .animate(rotationAnimation) //
                .on(R.id.pic_img_view);
    }
}
