package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.ZoomOutAnimation;

/**
 * Demo Activity for {@link ZoomOutAnimation}.
 */
public class ZoomOutViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        SparkleViewPagerLayout viewPagerLayout =
                (SparkleViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        ZoomOutAnimation zoomOutAnimation = new ZoomOutAnimation(Animation.ALL_PAGES);
        SparkleMotion.with(viewPagerLayout.getViewPager())
                .animate(zoomOutAnimation)
                .on(Animation.ANIMATION_ID_PAGE);
    }
}
