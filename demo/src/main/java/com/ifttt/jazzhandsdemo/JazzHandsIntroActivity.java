package com.ifttt.jazzhandsdemo;

import com.ifttt.jazzhands.JazzHands;
import com.ifttt.jazzhands.JazzHandsViewPagerLayout;
import com.ifttt.jazzhands.animations.ScaleAnimation;
import com.ifttt.jazzhands.animations.TranslationAnimation;
import com.ifttt.jazzhandsdemo.utils.ScreenConfig;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zhelu on 7/27/15.
 */
public class JazzHandsIntroActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        JazzHandsViewPagerLayout jazzHandsViewPagerLayout = new JazzHandsViewPagerLayout(this);
        jazzHandsViewPagerLayout.setBackgroundColor(getResources().getColor(android.R.color.holo_blue_dark));
        setContentView(jazzHandsViewPagerLayout);

        jazzHandsViewPagerLayout.getViewPager().setAdapter(new IntroPagerAdapter(this, true));

        JazzHands jazzHands = JazzHands.with(jazzHandsViewPagerLayout);

        LayoutInflater inflater = LayoutInflater.from(this);

        // Page 0 decors.
        JazzHandsViewPagerLayout.Decor page0Decor = new JazzHandsViewPagerLayout.Decor.Builder()
                .setContentView(inflater.inflate(R.layout.view_intro_page_0, jazzHandsViewPagerLayout, false))
                .setStartPage(0)
                .build();
        int distance = ScreenConfig.getScreenWidth(this);
        TranslationAnimation page0MoveOut = new TranslationAnimation(0, 0, true, distance, 0);
        jazzHands.animate(page0MoveOut).on(page0Decor);

        TranslationAnimation page3MoveOut = new TranslationAnimation(2, 2, true, -distance, 0);

        // Page 1 decors.
        TranslationAnimation page1MoveIn = new TranslationAnimation(0, 0, true, 0, 0);
        View decor1View = inflater.inflate(R.layout.view_intro_page_1, jazzHandsViewPagerLayout, false);
        JazzHandsViewPagerLayout.Decor page1Decor = new JazzHandsViewPagerLayout.Decor.Builder()
                .setContentView(decor1View)
                .setStartPage(0)
                .setEndPage(3)
                .build();
        decor1View.setTranslationX(distance);
        jazzHands.animate(page1MoveIn, page3MoveOut).on(page1Decor);

        // Page 2 decors.
        TranslationAnimation page2MoveIn = new TranslationAnimation(1, 1, true, 0, 0);
        View decor2View = inflater.inflate(R.layout.view_intro_decor_1, jazzHandsViewPagerLayout, false);
        decor2View.setTranslationX(distance);
        JazzHandsViewPagerLayout.Decor page2Decor = new JazzHandsViewPagerLayout.Decor.Builder()
                .setContentView(decor2View)
                .setStartPage(1)
                .setEndPage(3)
                .build();
        jazzHands.animate(page2MoveIn, page3MoveOut).on(page2Decor);

        // Page 3 decors.
        float iconSize = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 32, getResources().getDisplayMetrics());
        buildPhoneIcon(
                jazzHands, jazzHandsViewPagerLayout, inflater, distance, 300, -400, distance / 2 - iconSize, -1200);
        buildPhoneIcon(
                jazzHands, jazzHandsViewPagerLayout, inflater, distance, 600, -500, distance / 2 - iconSize, -1000);
        buildPhoneIcon(
                jazzHands, jazzHandsViewPagerLayout, inflater, distance, 900, -300, distance / 2 - iconSize, -800);
    }

    private static void buildPhoneIcon(
            JazzHands jazzHands, JazzHandsViewPagerLayout layout, LayoutInflater inflater,
            float windowWidth,
            float translationX, float translationY, float finalTranslationX, float finalTranslationY) {
        View icon = inflater.inflate(R.layout.view_intro_phone_icon, layout, false);
        int[] windowSize = ScreenConfig.getWindowSize(layout.getContext());
        icon.setTranslationX(windowSize[0] / 2);
        icon.setTranslationY(windowSize[1]);

        JazzHandsViewPagerLayout.Decor decor = new JazzHandsViewPagerLayout.Decor.Builder()
                .setContentView(icon)
                .setStartPage(2)
                .setEndPage(5)
                .build();
        ScaleAnimation scaleAnimation = new ScaleAnimation(2, 1f, 0.4f);
        TranslationAnimation translationAnimation = new TranslationAnimation(2, 2, true, translationX, translationY);
        TranslationAnimation finalTranslationAnimation =
                new TranslationAnimation(3, 3, true, finalTranslationX, finalTranslationY);
        TranslationAnimation moveOut = new TranslationAnimation(4, 4, true, -windowWidth + finalTranslationX, finalTranslationY);
        jazzHands.animate(translationAnimation, scaleAnimation, finalTranslationAnimation, moveOut)
                .on(decor);
    }

    private static class IntroPagerAdapter extends ViewPagerAdapter {

        private final boolean mUseLogin;
        private final LayoutInflater mLayoutInflater;
        private final Activity mActivity;

        public IntroPagerAdapter(Activity activity, boolean useLogin) {
            mActivity = activity;
            mLayoutInflater = LayoutInflater.from(activity);
            mUseLogin = useLogin;
        }

        @Override
        protected View getView(int position, ViewGroup container) {
            return new View(container.getContext());
        }

        @Override
        public int getCount() {
            return 6;
        }
    }


}
