package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import com.ifttt.jazzhands.Animation;
import com.ifttt.jazzhands.Decor;
import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.animations.AlphaAnimation;

/**
 * Demo Activity for {@link AlphaAnimation}.
 */
public class AlphaViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPagerLayout viewPagerLayout =
                (JazzHandsViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        AlphaAnimation alphaAnimation = new AlphaAnimation(Animation.ALL_PAGES, 0f, 1f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());

        JazzHands.with(viewPagerLayout) //
                .animate(alphaAnimation) //
                .on(R.id.pic_img_view);
    }
}
