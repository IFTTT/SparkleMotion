package com.ifttt.sparklemotiondemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateInterpolator;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.AlphaAnimation;

/**
 * Demo Activity for {@link AlphaAnimation}.
 */
public final class AlphaViewPagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        SparkleViewPagerLayout viewPagerLayout =
                (SparkleViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setInterpolator(new AccelerateInterpolator());

        SparkleMotion.with(viewPagerLayout) //
                .animate(alphaAnimation) //
                .on(R.id.pic_img_view);
    }
}
