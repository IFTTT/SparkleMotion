package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.ifttt.jazzhands.Animation;
import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.animations.ScaleAnimation;

/**
 * Demo Activity for {@link ScaleAnimation}.
 */
public class ScaleViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager =
                (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ScaleAnimation scaleAnimation = new ScaleAnimation(Animation.ALL_PAGES, 0.3f, 1f);

        JazzHands.with(viewPager) //
                .animate(scaleAnimation) //
                .on(R.id.pic_img_view);
    }
}
