package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.ifttt.sparklemotion.Decor;
import com.ifttt.sparklemotion.Page;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.AlphaAnimation;

/**
 * Demo Activity for {@link AlphaAnimation}.
 */
public final class AlphaViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sparkle_demo_layout);

        SparkleViewPagerLayout viewPagerLayout = (SparkleViewPagerLayout) findViewById(R.id.view_pager_layout);
        viewPagerLayout.getViewPager().setAdapter(new ViewPagerAdapter() {
            @Override
            protected View getView(int position, ViewGroup container) {
                return new View(container.getContext());
            }

            @Override
            public int getCount() {
                return 5;
            }
        });

        ImageView imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.ic_launcher);

        Decor decor = new Decor.Builder(imageView)
                .slideIn()
                .slideOut()
                .setPage(Page.singlePage(0))
                .build();

        SparkleMotion.with(viewPagerLayout).animate(new AlphaAnimation(Page.singlePage(0), 1f, 0.5f))
                .on(decor);

        //ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        //viewPager.setAdapter(new PagerAdapter());
        //
        //AlphaAnimation alphaAnimation = new AlphaAnimation(Page.allPages(), 0f, 1f);
        //alphaAnimation.setInterpolator(new AccelerateInterpolator());
        //
        //SparkleMotion.with(viewPager) //
        //        .animate(alphaAnimation) //
        //        .on(R.id.pic_img_view);
    }
}
