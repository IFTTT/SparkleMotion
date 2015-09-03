package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.animations.ScaleAnimation;

/**
 * Demo Activity for {@link ScaleAnimation}.
 */
public class ScaleViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.single_view_pager_layout);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());

        ScaleAnimation scaleAnimation = new ScaleAnimation(Animation.ALL_PAGES, 0.3f, 1f);

        SparkleMotion.with(viewPager) //
                .animate(scaleAnimation) //
                .on(R.id.pic_img_view);
    }
}
