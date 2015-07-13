package com.ifttt.jazzhands;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.ifttt.jazzhands.animations.Animation;

import java.util.ArrayList;

/**
 * Created by zhelu on 7/12/15.
 */
public class JazzHandsViewPagerLayout extends FrameLayout {

    private JazzHandsViewPager mJazzHandsViewPager;

    public JazzHandsViewPagerLayout(Context context) {
        super(context);
        init();
    }

    public JazzHandsViewPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public JazzHandsViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public JazzHandsViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        // Add JazzHandsViewPager.
        mJazzHandsViewPager = new JazzHandsViewPager(getContext());
        addView(mJazzHandsViewPager);
    }

    public JazzHandsViewPager getViewPager() {
        return mJazzHandsViewPager;
    }

    public void addDecorViewAnimations(View decorView, Animation... animations) {
        mJazzHandsViewPager.getJazzHandsAnimationPresenter().addAnimation(decorView, animations);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return super.getChildDrawingOrder(childCount, i);
    }
}
