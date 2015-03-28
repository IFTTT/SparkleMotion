package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.PathAnimation;

/**
 * Created by zhelu on 3/27/15.
 */
public class PathViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager viewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        viewPager.setAdapter(new PagerAdapter());
        viewPager.setOffscreenPageLimit(5);

        Path path = new Path();
        RectF rectF = new RectF(0, 0, 400, 200);
        path.quadTo(75, 300, 150, 0);
        path.quadTo(225, -100, 300, 0);


        PathAnimation pathAnimation = new PathAnimation(0, 3, true, path);

        JazzHands.with(viewPager)
                .reverseDrawingOrder()
                .animate(pathAnimation)
                .on(R.id.icon_1);
    }
}
