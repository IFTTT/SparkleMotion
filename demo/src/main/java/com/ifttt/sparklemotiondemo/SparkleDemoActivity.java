package com.ifttt.sparklemotiondemo;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import com.ifttt.sparklemotion.Page;
import com.ifttt.sparklemotion.SparkleMotion;
import com.ifttt.sparklemotion.SparkleViewPagerLayout;
import com.ifttt.sparklemotion.animations.ParallaxAnimation;
import com.ifttt.sparklemotion.animations.RotationAnimation;
import com.ifttt.sparklemotion.animations.ScaleAnimation;
import com.ifttt.sparklemotion.animations.TranslationAnimation;

/**
 * Main Activity for Sparkle Motion demo, including a nice animated ViewPager animation and entrance to
 * other demo Activities.
 */
public final class SparkleDemoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sparkle_demo_layout);

        SparkleViewPagerLayout sparkleViewPagerLayout = (SparkleViewPagerLayout) findViewById(R.id.view_pager);

        SparkleMotion sparkleMotion = SparkleMotion.with(sparkleViewPagerLayout);

        // Build Decors for different pages.
        buildDecorForPage0(sparkleViewPagerLayout, sparkleMotion);
        buildDecorForPage1(sparkleViewPagerLayout, sparkleMotion);
        buildDecorForPage2(sparkleViewPagerLayout, sparkleMotion);
        buildDecor3(sparkleViewPagerLayout, sparkleMotion);

        // Build Sparkle Motion text animations.
        float motionTranslationY = getResources().getDimension(R.dimen.motion_translation_y);
        Page allPages = Page.allPages();
        sparkleMotion.animate(new RotationAnimation(allPages, 0, -90)).on(R.id.sparkle_img, R.id.motion_img);
        sparkleMotion.animate(
                new TranslationAnimation(allPages, 0, 0, 0, motionTranslationY, false)).on(R.id.motion_img);

        // Build IFTTT cloud animation.
        sparkleMotion.animate(new ParallaxAnimation(allPages, -2.0f)).on(R.id.ifttt_cloud);

        // Build List animations.
        sparkleMotion.animate(new ParallaxAnimation(allPages, -3.0f)).on(R.id.alpha_btn);
        sparkleMotion.animate(new ParallaxAnimation(allPages, -2.0f)).on(R.id.scale_btn);
        sparkleMotion.animate(new ParallaxAnimation(allPages, -0.5f)).on(R.id.rotation_btn);
        sparkleMotion.animate(new ParallaxAnimation(allPages, -0.25f)).on(R.id.parallax_btn);
        sparkleMotion.animate(new ParallaxAnimation(allPages, -0.1f)).on(R.id.zoom_out_btn);

        sparkleViewPagerLayout.getViewPager().setAdapter(new PagerAdapter());
    }

    /**
     * Build the first Decor: a star with a scale animation that will be used as a background for other pages.
     */
    private void buildDecorForPage0(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_0_star, parent, false);
        Decor decor = new Decor.Builder().setContentView(view).setPage(Page.pageRange(0, 4)).behindViewPage().build();

        ScaleAnimation scaleAnimation = new ScaleAnimation(Page.singlePage(0), 1f, 1f, 7f, 7f);
        sparkleMotion.animate(scaleAnimation).on(decor);
    }

    /**
     * Build the second page Decors: a music stand and notes, with translation animations that animates in as
     * the ViewPager scrolls.
     */
    private void buildDecorForPage1(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {

        int windowWidth = ScreenConfig.getWindowSize(this)[0];

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_1, parent, false);
        Decor decor = new Decor.Builder().setContentView(view).setPage(Page.pageRange(0, 2)).build();

        TranslationAnimation translationAnimation =
                new TranslationAnimation(Page.singlePage(0), windowWidth, 0, 0, 0, true);
        sparkleMotion.animate(translationAnimation).on(decor);

        View notes = LayoutInflater.from(parent.getContext()).inflate(R.layout.sparkle_page_1_notes, parent, false);
        Decor notesDecor = new Decor.Builder().setContentView(notes).setPage(Page.pageRange(0, 2)).build();

        TranslationAnimation translationAnimation1 =
                new TranslationAnimation(Page.singlePage(0), windowWidth, 0, 0, 0, true);
        translationAnimation1.setInterpolator(new AccelerateInterpolator());
        sparkleMotion.animate(translationAnimation1).on(notesDecor);

        int windowHeight = ScreenConfig.getWindowSize(this)[1];
        TranslationAnimation translationAnimation2 =
                new TranslationAnimation(Page.singlePage(1), 0, 0, 0, windowHeight, true);
        sparkleMotion.animate(translationAnimation2).on(decor, notesDecor);
    }

    /**
     * Build the third page Decors: a {@link PaperPlaneView } that draws a path within the page, and
     * two clouds the slide down as the page scrolls.
     */
    private void buildDecorForPage2(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {

        // Setup PaperPlaneView.
        final PaperPlaneView view = (PaperPlaneView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sparkle_page_2_plane, parent, false);

        Decor decor = new Decor.Builder().setContentView(view)
                .setPage(Page.pageRange(1, 2))
                .behindViewPage()
                .slideOut()
                .build();

        sparkleMotion.animate(new Animation(Page.singlePage(1)) {
            @Override
            public void onAnimate(View v, float offset, float offsetInPixel) {
                offset = Math.abs(offset);
                view.animate(offset);
            }
        }).on(decor);

        final int cloudMargin = getResources().getDimensionPixelOffset(R.dimen.icon_cloud_margin);
        final Drawable cloud = ContextCompat.getDrawable(this, R.drawable.cloud);

        // Setup small cloud.
        FrameLayout.LayoutParams lpSmallCloud =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpSmallCloud.leftMargin = cloudMargin;
        lpSmallCloud.topMargin = cloudMargin;

        ImageView smallCloud = new ImageView(parent.getContext());
        smallCloud.setLayoutParams(lpSmallCloud);
        smallCloud.setTranslationY(-cloud.getIntrinsicHeight() - lpSmallCloud.topMargin);
        smallCloud.setImageDrawable(cloud);

        // Setup large cloud.
        FrameLayout.LayoutParams lpBigCloud =
                new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lpBigCloud.gravity = Gravity.RIGHT;
        lpBigCloud.topMargin = (int) (cloud.getIntrinsicHeight() * 1.6f);

        ImageView bigCloud = new ImageView(parent.getContext());
        bigCloud.setLayoutParams(lpBigCloud);
        bigCloud.setTranslationY(-cloud.getIntrinsicHeight() * 1.6f - lpBigCloud.topMargin);
        bigCloud.setScaleX(1.6f);
        bigCloud.setScaleY(1.6f);
        bigCloud.setImageDrawable(cloud);

        Page page = Page.pageRange(1, 2);
        Decor cloudDecor1 =
                new Decor.Builder().setContentView(smallCloud).setPage(page).slideOut().build();
        Decor cloudDecor2 =
                new Decor.Builder().setContentView(bigCloud).setPage(page).slideOut().build();

        TranslationAnimation translationAnimation1 =
                new TranslationAnimation(Page.singlePage(1), 0, smallCloud.getTranslationY(), 0, 0, true);
        TranslationAnimation translationAnimation2 =
                new TranslationAnimation(Page.singlePage(1), 0, bigCloud.getTranslationY(), 0, 0, true);

        sparkleMotion.animate(translationAnimation1).on(cloudDecor1);
        sparkleMotion.animate(translationAnimation2).on(cloudDecor2);
    }

    /**
     * Build the fourth page Decors: a sun that slides down as the ViewPager scrolls.
     */
    private void buildDecor3(SparkleViewPagerLayout parent, SparkleMotion sparkleMotion) {
        ImageView sunImageView = new ImageView(parent.getContext());
        sunImageView.setImageResource(R.drawable.sun);

        int sunSize = getResources().getDimensionPixelSize(R.dimen.icon_sun_size);
        FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(sunSize, sunSize);
        sunImageView.setLayoutParams(lp);
        sunImageView.setTranslationY(-sunSize);
        sunImageView.setTranslationX(sunSize);

        Decor decor = new Decor.Builder().setContentView(sunImageView).setPage(Page.pageRange(2, 4)).build();

        TranslationAnimation translationAnimation =
                new TranslationAnimation(Page.singlePage(2), sunSize, -sunSize, -sunSize / 3f, -sunSize / 3f, true);
        sparkleMotion.animate(translationAnimation).on(decor);
    }

    private static class PagerAdapter extends ViewPagerAdapter {

        @Override
        protected View getView(int position, ViewGroup container) {
            switch (position) {
                case 0:
                    return buildFirstPage(container);
                case 2:
                    return buildSecondPage(container);
                case 3:
                    return buildThirdPage(container);
                case 4:
                    return buildDemoPage(container);
                default:
                    return new View(container.getContext());
            }
        }

        @Override
        public int getCount() {
            return 5;
        }

        private View buildFirstPage(ViewGroup container) {
            return LayoutInflater.from(container.getContext()).inflate(R.layout.sparkle_page_0, container, false);
        }

        private View buildSecondPage(ViewGroup container) {
            return LayoutInflater.from(container.getContext()).inflate(R.layout.sparkle_page_2, container, false);
        }

        private View buildThirdPage(ViewGroup container) {
            return LayoutInflater.from(container.getContext()).inflate(R.layout.sparkle_page_3, container, false);
        }

        private View buildDemoPage(final ViewGroup container) {
            View root = LayoutInflater.from(container.getContext()).inflate(R.layout.sparkle_page_4, container, false);
            final Context context = container.getContext();
            root.findViewById(R.id.alpha_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, AlphaViewPagerActivity.class);
                    context.startActivity(i);
                }
            });

            root.findViewById(R.id.scale_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ScaleViewPagerActivity.class);
                    context.startActivity(i);
                }
            });

            root.findViewById(R.id.rotation_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, RotationViewPagerActivity.class);
                    context.startActivity(i);
                }
            });

            root.findViewById(R.id.parallax_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ParallaxViewPagerActivity.class);
                    context.startActivity(i);
                }
            });

            root.findViewById(R.id.zoom_out_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, ZoomOutViewPagerActivity.class);
                    context.startActivity(i);
                }
            });

            return root;
        }
    }
}
