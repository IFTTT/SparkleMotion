package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ifttt.jazzhands.Animation;
import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.animations.ZoomOutAnimation;

/**
 * Demo Activity for {@link ZoomOutAnimation}.
 */
public class ZoomOutViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager =
                (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ZoomOutAnimation zoomOutAnimation = new ZoomOutAnimation(Animation.ALL_PAGES);
        JazzHands.with(viewPager)
                .animate(zoomOutAnimation)
                .on(Animation.ANIMATION_ID_PAGE);
    }
}
