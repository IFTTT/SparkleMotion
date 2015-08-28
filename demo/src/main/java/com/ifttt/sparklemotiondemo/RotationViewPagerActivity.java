package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.animation.AccelerateInterpolator;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.RotationAnimation;

/**
 * Demo Activity for {@link RotationAnimation}.
 */
public class RotationViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        SparkleViewPagerLayout viewPagerLayout =
                (SparkleViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        RotationAnimation rotationAnimation = new RotationAnimation(Animation.ALL_PAGES, 360);
        rotationAnimation.setInterpolator(new AccelerateInterpolator());

        SparkleMotion.with(viewPagerLayout) //
                .animate(rotationAnimation) //
                .on(R.id.pic_img_view);
    }
}
