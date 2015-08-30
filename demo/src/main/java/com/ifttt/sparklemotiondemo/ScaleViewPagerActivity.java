package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.ScaleAnimation;

/**
 * Demo Activity for {@link ScaleAnimation}.
 */
public class ScaleViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        SparkleViewPagerLayout viewPagerLayout =
                (SparkleViewPagerLayout) findViewById(R.id.view_pager);
        viewPagerLayout.getViewPager().setAdapter(new PagerAdapter());

        ScaleAnimation scaleAnimation = new ScaleAnimation(0.3f, 0.3f, 1f, 1f);

        SparkleMotion.with(viewPagerLayout) //
                .animate(scaleAnimation) //
                .on(R.id.pic_img_view);
    }
}
