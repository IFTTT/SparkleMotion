package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.animations.ScaleAnimation;

/**
 * Demo Activity for {@link ScaleAnimation}.
 */
public final class ScaleViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ScaleAnimation scaleAnimation = new ScaleAnimation(1f, 1f, 0.3f, 0.3f);

        SparkleMotion.with(viewPager) //
                .animate(scaleAnimation) //
                .on(R.id.pic_img_view);
    }
}
