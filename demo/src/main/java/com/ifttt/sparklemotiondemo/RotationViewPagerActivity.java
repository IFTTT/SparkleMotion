package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
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

        setContentView(R.layout.single_view_pager_layout);


        ViewPager viewPager =
                (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());
        RotationAnimation rotationAnimation = new RotationAnimation(Animation.ALL_PAGES, 360);
        rotationAnimation.setInterpolator(new AccelerateInterpolator());


        SparkleMotion.with(viewPager) //
                .animate(rotationAnimation) //
                .on(R.id.pic_img_view);
    }
}
