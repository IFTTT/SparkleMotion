package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.graphics.Path;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.ifttt.sparklemotion.Decor;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.PathAnimation;
import com.ifttt.sparklemotion.animations.RotationAnimation;
import com.ifttt.sparklemotion.animations.ScaleAnimation;
import com.ifttt.sparklemotion.animations.TranslationAnimation;

public final class SparkleDemoActivity extends Activity {

    private SparkleViewPagerLayout mSparkleViewPagerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        mSparkleViewPagerLayout = (SparkleViewPagerLayout) findViewById(R.id.view_pager);

        SparkleMotion sparkleMotion = SparkleMotion.with(mSparkleViewPagerLayout);

        buildDecor0(mSparkleViewPagerLayout, sparkleMotion);
        buildDecor1(mSparkleViewPagerLayout, sparkleMotion);
        buildDecor2(mSparkleViewPagerLayout, sparkleMotion);
        buildDecor3(mSparkleViewPagerLayout, sparkleMotion);

        mSparkleViewPagerLayout.getViewPager().setAdapter(new PagerAdapter());
    }

    private void buildDecor0(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_0, parent, false);
        Decor decor = new Decor.Builder().setContentView(view).setStartPage(0).setEndPage(4).behindViewPage().build();

        ScaleAnimation scaleAnimation = new ScaleAnimation(0, 7f, 1f);
        sparkleMotion.animate(scaleAnimation).on(decor);
    }

    private void buildDecor1(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_1, parent, false);
        Decor decor = new Decor.Builder().setContentView(view).setStartPage(0).setEndPage(2).build();

        TranslationAnimation translationAnimation = new TranslationAnimation(0, false, 0, 0);
        sparkleMotion.animate(translationAnimation).on(decor);

        View notes = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_1_notes, parent, false);
        Decor notesDecor = new Decor.Builder().setContentView(notes).setStartPage(0).setEndPage(2).build();

        TranslationAnimation translationAnimation1 = new TranslationAnimation(0, false, 0, 0);
        translationAnimation1.setInterpolator(new AccelerateInterpolator());
        sparkleMotion.animate(translationAnimation1).on(notesDecor);

        TranslationAnimation translationAnimation2 = new TranslationAnimation(1, false, 0, 3000);
        sparkleMotion.animate(translationAnimation2).on(decor, notesDecor);
    }

    private void buildDecor2(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_2_plane, parent, false);

        Decor decor = new Decor.Builder()
                .setContentView(view)
                .setStartPage(1)
                .setEndPage(2)
                .build();

        Path path = new Path();
        path.quadTo(-100, -1500, -2000, -2000);

        PathAnimation pathAnimation = new PathAnimation(1, true, path);

        RotationAnimation rotationAnimation = new RotationAnimation(1, 45);

        sparkleMotion.animate(pathAnimation, rotationAnimation).on(decor);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        ImageView cloud1 = new ImageView(parent.getContext());
        cloud1.setLayoutParams(lp);
        cloud1.setTranslationX(50);
        cloud1.setTranslationY(-300);
        cloud1.setImageResource(R.drawable.cloud);
        ImageView cloud2 = new ImageView(parent.getContext());
        cloud2.setLayoutParams(lp);
        cloud2.setTranslationX(600);
        cloud2.setTranslationY(-300);
        cloud2.setScaleX(1.3f);
        cloud2.setScaleY(1.3f);
        cloud2.setImageResource(R.drawable.cloud);

        Decor cloudDecor1 = new Decor.Builder()
                .setContentView(cloud1)
                .setStartPage(1)
                .setEndPage(2)
                .slideOut()
                .build();
        Decor cloudDecor2 = new Decor.Builder()
                .setContentView(cloud2)
                .setStartPage(1)
                .setEndPage(2)
                .build();

        TranslationAnimation translationAnimation1 = new TranslationAnimation(1, true, 50, 0);
        TranslationAnimation translationAnimation2 = new TranslationAnimation(1, true, 600, 0);

        sparkleMotion.animate(translationAnimation1).on(cloudDecor1);
        sparkleMotion.animate(translationAnimation2).on(cloudDecor2);

    }

    private void buildDecor3(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {
        ImageView sunImageView = new ImageView(parent.getContext());
        sunImageView.setImageResource(R.drawable.sun);

        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        sunImageView.setLayoutParams(lp);

        Decor decor = new Decor.Builder()
                .setContentView(sunImageView)
                .setStartPage(2)
                .setEndPage(3)
                .build();

        TranslationAnimation translationAnimation = new TranslationAnimation(2, true, -200, -200);
        sparkleMotion.animate(translationAnimation).on(decor);
    }

    private static class PagerAdapter extends ViewPagerAdapter {

        @Override
        protected View getView(int position, ViewGroup container) {
            switch (position) {
                case 5:
                    return buildSecondPage(container);
                default:
                    return buildFirstPage(container);
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        private View buildFirstPage(ViewGroup container) {
            return new View(container.getContext());
        }

        private View buildSecondPage(ViewGroup container) {
            View view = LayoutInflater.from(container.getContext()).inflate(R.layout.sparkle_page_1, container, false);

            return view;
        }
    }
}
