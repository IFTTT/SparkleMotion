package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.PathAnimation;
import com.ifttt.jazzhands.animations.RotationAnimation;
import com.ifttt.jazzhands.animations.ScaleAnimation;
import com.ifttt.jazzhands.animations.TranslationAnimation;

/**
 * Comprehensive demo.
 */
public class StoryViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPager viewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(4);
        viewPager.setPageMargin(5);
        viewPager.setPageMarginDrawable(new ColorDrawable(Color.BLACK));
        viewPager.setAdapter(new PagerAdapter(this));

        JazzHands jazzHands = JazzHands.with(viewPager).reverseDrawingOrder();
        buildPage0Animations(jazzHands);
        buildPage1Animations(jazzHands);
        buildPage2Animations(jazzHands);
    }


    private void buildPage0Animations(JazzHands jazzHands) {
        Path path = new Path();
        path.quadTo(75, 100, 150, 0);
        path.quadTo(225, -100, 300, 0);
        path.quadTo(375, 100, 450, 0);
        path.quadTo(525, -100, 600, 0);

        PathAnimation pathAnimation = new PathAnimation(0, 0, true, path);

        jazzHands.animate(pathAnimation).on(R.id.page_0_droid);
    }

    private void buildPage1Animations(JazzHands jazzHands) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 1, 1f, 1.5f);
        jazzHands.animate(scaleAnimation)
                .on(R.id.page_1_drod_0, R.id.page_1_drod_1, R.id.page_1_drod_2, R.id.page_1_drod_3);

        TranslationAnimation droid0Translation = new TranslationAnimation(0, 1, false, 100, -400);
        TranslationAnimation droid1Translation = new TranslationAnimation(0, 1, false, 50, -200);
        TranslationAnimation droid2Translation = new TranslationAnimation(0, 1, false, 50, 200);
        TranslationAnimation droid3Translation = new TranslationAnimation(0, 1, false, 100, 400);

        jazzHands.animate(droid0Translation).on(R.id.page_1_drod_0);
        jazzHands.animate(droid1Translation).on(R.id.page_1_drod_1);
        jazzHands.animate(droid2Translation).on(R.id.page_1_drod_2);
        jazzHands.animate(droid3Translation).on(R.id.page_1_drod_3);
    }

    private void buildPage2Animations(JazzHands jazzHands) {
        Path droid0Path = new Path();
        RectF droid0Rect = new RectF(0, -300, 600, 300);
        droid0Path.addOval(droid0Rect, Path.Direction.CW);

        RotationAnimation rotationAnimation = new RotationAnimation(1, 2, 360);

        PathAnimation pathAnimation = new PathAnimation(1, 2, true, droid0Path);
        jazzHands.animate(pathAnimation, rotationAnimation).on(R.id.page_0_droid);
    }

    private static class PagerAdapter extends ViewPagerAdapter {

        private LayoutInflater mLayoutInflater;

        public PagerAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        protected View getView(int position, ViewGroup container) {
            switch(position) {
                case 0:
                    return mLayoutInflater.inflate(R.layout.story_page_0, container, false);
                case 1:
                    return mLayoutInflater.inflate(R.layout.story_page_1, container, false);
                case 2:
                case 3:
                    return mLayoutInflater.inflate(R.layout.story_page_2, container, false);

            }

            return null;
        }

        @Override
        public int getCount() {
            return 4;
        }
    }
}
