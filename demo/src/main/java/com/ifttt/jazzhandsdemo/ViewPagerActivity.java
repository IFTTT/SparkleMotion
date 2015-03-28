package com.ifttt.jazzhandsdemo;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.DecelerateInterpolator;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPager;
import com.ifttt.jazzhands.animations.Animation;
import com.ifttt.jazzhands.animations.ScaleAnimation;
import com.ifttt.jazzhands.animations.TranslationAnimation;

/**
 * Created by zhelu on 3/9/15.
 */
public class ViewPagerActivity extends Activity {

    private JazzHandsViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.view_pager_layout);

        mViewPager = (JazzHandsViewPager) findViewById(R.id.view_pager);
        mViewPager.setAdapter(new PagerAdapter());

        DisplayMetrics metrics = this.getResources().getDisplayMetrics();

        JazzHands jazzHands = JazzHands.with(mViewPager).reverseDrawingOrder().lift(2);

        TranslationAnimation translate =
                new TranslationAnimation(0, 1, true, metrics.widthPixels, 0f);
        translate.setInterpolator(new DecelerateInterpolator(1.5f));

        jazzHands.animate(translate).on(R.id.icon_1, R.id.icon_2, R.id.icon_3, R.id.icon_4);

        TranslationAnimation secondPageTranslation =
                new TranslationAnimation(1, 1, true, 0f, 0f);

        jazzHands.animate(secondPageTranslation).on(R.id.recipe_maker, R.id.second_page_icon);

        TranslationAnimation thirdPageTranslation1 =
                new TranslationAnimation(2, 2, true, 128f, metrics.heightPixels);
        thirdPageTranslation1.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationRunning(float fraction) {
                Log.d(getClass().getSimpleName(), fraction + " ");
            }
        });

        TranslationAnimation thirdPageTranslation2 =
                new TranslationAnimation(2, 2, true, 0f, metrics.heightPixels);

        TranslationAnimation thirdPageTranslation3 =
                new TranslationAnimation(2, 2, true, -128f, metrics.heightPixels);

        TranslationAnimation thirdPageTranslation4 =
                new TranslationAnimation(2, 2, true, 0, metrics.heightPixels);

        ScaleAnimation fourthPageScale =
                new ScaleAnimation(2, 2, 1.5f, 1f);


        jazzHands.animate(thirdPageTranslation1, fourthPageScale).on(R.id.four_page_text1);
        jazzHands.animate(thirdPageTranslation2, fourthPageScale).on(R.id.four_page_text2);
        jazzHands.animate(thirdPageTranslation3, fourthPageScale).on(R.id.four_page_text3);
        jazzHands.animate(thirdPageTranslation4).on(R.id.bottom_phone);

        TranslationAnimation fifthPageTranslation1 =
                new TranslationAnimation(3, 3, true, 36f, -464f);

        TranslationAnimation fifthPageTranslation2 =
                new TranslationAnimation(3, 3, true, -140f, -56f);

        TranslationAnimation fifthPageTranslation3 =
                new TranslationAnimation(3, 3, true, -300f, 256f);

        ScaleAnimation fifthPageScale =
                new ScaleAnimation(3, 3, 1.5f, 1f);

        jazzHands.animate(fifthPageTranslation1, fifthPageScale).on(R.id.four_page_text1);
        jazzHands.animate(fifthPageTranslation2, fifthPageScale).on(R.id.four_page_text2);
        jazzHands.animate(fifthPageTranslation3, fifthPageScale).on(R.id.four_page_text3);

        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // A workaround to keep pages within the screen, so that the cross page animations
            // can be run normally.
            mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    int pageWidth = page.getWidth();
                    if (position >= -1
                            && position < 0
                            && mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                        page.setTranslationX(1 + Math.max(pageWidth * (Math.abs(((int) position)) - 1), 0));
                    }
                }
            });
        }
    }

}
