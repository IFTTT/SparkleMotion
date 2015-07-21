package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;

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

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPagerLayout viewPagerLayout = (JazzHandsViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());


        ZoomOutAnimation zoomOutAnimation = new ZoomOutAnimation(Animation.ALL_PAGES);
        JazzHands.with(viewPagerLayout.getViewPager())
                .animate(zoomOutAnimation)
                .on(Animation.ANIMATION_ID_PAGE);
    }
}
