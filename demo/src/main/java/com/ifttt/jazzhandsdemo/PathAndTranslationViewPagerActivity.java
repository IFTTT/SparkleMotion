package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.ifttt.jazzhands.Decor;
import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.animations.PathAnimation;
import com.ifttt.jazzhands.animations.RotationAnimation;
import com.ifttt.jazzhands.animations.ScaleAnimation;
import com.ifttt.jazzhands.animations.TranslationAnimation;

/**
 * Comprehensive demo for {@link PathAnimation} and {@link TranslationAnimation}.
 */
public class PathAndTranslationViewPagerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        JazzHandsViewPagerLayout viewPagerLayout =
                (JazzHandsViewPagerLayout) findViewById(R.id.view_pager_layout);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewPagerLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        viewPager.setPageMargin(5);
        viewPager.setPageMarginDrawable(new ColorDrawable(Color.BLACK));
        viewPager.setAdapter(new PagerAdapter(this));

        ImageView page0Droid = new ImageView(this);
        page0Droid.setImageResource(R.mipmap.ic_launcher);
        FrameLayout.LayoutParams lp =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.gravity = Gravity.CENTER_VERTICAL;
        page0Droid.setLayoutParams(lp);
        Decor decor = new Decor.Builder() //
                .setContentView(page0Droid) //
                .setStartPage(0) //
                .setEndPage(2) //
                .behindViewPage() //
                .build();

        JazzHands jazzHands = JazzHands.with(viewPagerLayout).reverseDrawingOrder();
        buildPage0Animations(jazzHands, decor);
        buildPage1Animations(jazzHands);
        buildPage2Animations(jazzHands, decor);
    }

    private void buildPage0Animations(JazzHands jazzHands, Decor decor) {
        Path path = new Path();
        path.quadTo(75, 100, 150, 0);
        path.quadTo(225, -100, 300, 0);
        path.quadTo(375, 100, 450, 0);
        path.quadTo(525, -100, 600, 0);

        PathAnimation pathAnimation = new PathAnimation(0, 0, true, path);
        jazzHands.animate(pathAnimation).on(decor);
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

    private void buildPage2Animations(JazzHands jazzHands, Decor decor) {
        Path droid0Path = new Path();
        RectF droid0Rect = new RectF(0, -300, 600, 300);
        droid0Path.addOval(droid0Rect, Path.Direction.CW);

        RotationAnimation rotationAnimation = new RotationAnimation(1, 2, 360);

        PathAnimation pathAnimation = new PathAnimation(1, 1, true, droid0Path);
        jazzHands.animate(rotationAnimation, pathAnimation).on(decor);
    }

    private static class PagerAdapter extends ViewPagerAdapter {

        private LayoutInflater mLayoutInflater;

        public PagerAdapter(Context context) {
            mLayoutInflater = LayoutInflater.from(context);
        }

        @Override
        protected View getView(int position, ViewGroup container) {
            switch (position) {
                case 0:
                    return mLayoutInflater.inflate(R.layout.path_translation_page_0, container,
                            false);
                case 1:
                    return mLayoutInflater.inflate(R.layout.path_translation_page_1, container,
                            false);
                case 2:
                    return mLayoutInflater.inflate(R.layout.path_translation_page_2, container,
                            false);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
