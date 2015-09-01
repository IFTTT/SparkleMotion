package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.animations.ZoomOutAnimation;

/**
 * Demo Activity for {@link ZoomOutAnimation}.
 */
public class ZoomOutViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ZoomOutAnimation zoomOutAnimation = new ZoomOutAnimation(Animation.ALL_PAGES);

        SparkleMotion.with(viewPager).animate(zoomOutAnimation).on(Animation.ANIMATION_ID_PAGE);
    }
}
