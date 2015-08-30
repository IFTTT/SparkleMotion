package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.graphics.Path;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.ifttt.sparklemotion.Animation;
import com.ifttt.sparklemotion.Decor;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.PathAnimation;
import com.ifttt.sparklemotion.animations.RotationAnimation;
import com.ifttt.sparklemotion.animations.ScaleAnimation;
import com.ifttt.sparklemotion.animations.TranslationAnimation;

public final class SparkleDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        SparkleViewPagerLayout sparkleViewPagerLayout = (SparkleViewPagerLayout) findViewById(R.id.view_pager);

        SparkleMotion sparkleMotion = SparkleMotion.with(sparkleViewPagerLayout);

        buildDecor0(sparkleViewPagerLayout, sparkleMotion);
        buildDecor1(sparkleViewPagerLayout, sparkleMotion);
        buildDecor2(sparkleViewPagerLayout, sparkleMotion);
        buildDecor3(sparkleViewPagerLayout, sparkleMotion);

        int iftttCloudTranslationY = getResources().getDimensionPixelOffset(R.dimen.ifttt_cloud_translation_x);
        TranslationAnimation iftttCloudTranslation = new TranslationAnimation(0, 0, iftttCloudTranslationY, 0, false);
        iftttCloudTranslation.setInterpolator(new AccelerateInterpolator());
        sparkleMotion.animate(iftttCloudTranslation).on(R.id.ifttt_cloud);

        sparkleViewPagerLayout.getViewPager().setAdapter(new PagerAdapter());
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

        TranslationAnimation translationAnimation = new TranslationAnimation(0, view.getTranslationX(), 0, 0, 0, false);
        sparkleMotion.animate(translationAnimation).on(decor);

        View notes = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_1_notes, parent, false);
        Decor notesDecor = new Decor.Builder().setContentView(notes).setStartPage(0).setEndPage(2).build();

        TranslationAnimation translationAnimation1 = new TranslationAnimation(0, notes.getTranslationX(),
                notes.getTranslationY(), 0, 0, false);
        translationAnimation1.setInterpolator(new AccelerateInterpolator());
        sparkleMotion.animate(translationAnimation1).on(notesDecor);

        // TODO: change 3000 to something less magic.
        TranslationAnimation translationAnimation2 = new TranslationAnimation(1, 0, 0, 0, 3000, false);
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

        PathAnimation pathAnimation = new PathAnimation(1, path, true);

        RotationAnimation rotationAnimation = new RotationAnimation(1, 45);

        sparkleMotion.animate(pathAnimation, rotationAnimation).on(decor);

        final int cloudMargin = getResources().getDimensionPixelOffset(R.dimen.icon_cloud_margin);
        final Drawable cloud = ContextCompat.getDrawable(this, R.drawable.cloud);

        FrameLayout.LayoutParams lpSmallCloud = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lpSmallCloud.leftMargin = cloudMargin;
        lpSmallCloud.topMargin = cloudMargin;

        ImageView smallCloud = new ImageView(parent.getContext());
        smallCloud.setLayoutParams(lpSmallCloud);
        smallCloud.setTranslationY(-cloud.getIntrinsicHeight() - lpSmallCloud.topMargin);
        smallCloud.setImageDrawable(cloud);

        FrameLayout.LayoutParams lpBigCloud = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        lpBigCloud.gravity = Gravity.RIGHT;
        lpBigCloud.topMargin = (int) (cloud.getIntrinsicHeight() * 1.6f);

        ImageView bigCloud = new ImageView(parent.getContext());
        bigCloud.setLayoutParams(lpBigCloud);
        bigCloud.setTranslationY(-cloud.getIntrinsicHeight() * 1.6f - lpBigCloud.topMargin);
        bigCloud.setScaleX(1.6f);
        bigCloud.setScaleY(1.6f);
        bigCloud.setImageDrawable(cloud);

        Decor cloudDecor1 = new Decor.Builder()
                .setContentView(smallCloud)
                .setStartPage(1)
                .setEndPage(2)
                .slideOut()
                .build();
        Decor cloudDecor2 = new Decor.Builder()
                .setContentView(bigCloud)
                .setStartPage(1)
                .setEndPage(2)
                .slideOut()
                .build();

        TranslationAnimation translationAnimation1 = new TranslationAnimation(1, 0, smallCloud.getTranslationY(),
                0,
                0, true);
        TranslationAnimation translationAnimation2 = new TranslationAnimation(1, 0, bigCloud.getTranslationY(), 0,
                0, true);

        sparkleMotion.animate(translationAnimation1).on(cloudDecor1);
        sparkleMotion.animate(translationAnimation2).on(cloudDecor2);

    }

    private void buildDecor3(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {
        ImageView sunImageView = new ImageView(parent.getContext());
        sunImageView.setImageResource(R.drawable.sun);

        int sunSize = getResources().getDimensionPixelSize(R.dimen.icon_sun_size);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                sunSize,
                sunSize);
        sunImageView.setLayoutParams(lp);
        sunImageView.setTranslationY(-sunSize);
        sunImageView.setTranslationX(sunSize);

        Decor decor = new Decor.Builder()
                .setContentView(sunImageView)
                .setStartPage(2)
                .setEndPage(3)
                .slideOut()
                .build();

        TranslationAnimation translationAnimation = new TranslationAnimation(2, sunSize, -sunSize, -sunSize / 3f,
                -sunSize / 3f, true);
        sparkleMotion.animate(translationAnimation).on(decor);
    }

    private static class PagerAdapter extends ViewPagerAdapter {

        @Override
        protected View getView(int position, ViewGroup container) {
            switch (position) {
                case 2:
                    return buildSecondPage(container);
                case 3:
                    return buildThirdPage(container);
                default:
                    return buildFirstPage(container);
            }
        }

        @Override
        public int getCount() {
            return 4;
        }

        private View buildFirstPage(ViewGroup container) {
            return new View(container.getContext());
        }

        private View buildSecondPage(ViewGroup container) {
            return LayoutInflater.from(container.getContext()).inflate(R.layout.sparkle_page_2, container, false);
        }

        private View buildThirdPage(ViewGroup container) {
            return LayoutInflater.from(container.getContext()).inflate(R.layout.sparkle_page_3, container, false);
        }
    }
}
