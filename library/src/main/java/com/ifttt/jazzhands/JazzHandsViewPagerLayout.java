package com.ifttt.jazzhands;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by zhelu on 7/12/15.
 */
public class JazzHandsViewPagerLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private JazzHandsViewPager mJazzHandsViewPager;

    private ArrayList<Decor> mDecors = new ArrayList<>();

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

    private void init() {
        // Add JazzHandsViewPager.
        mJazzHandsViewPager = new JazzHandsViewPager(getContext());
        addView(mJazzHandsViewPager);

        mJazzHandsViewPager.addOnPageChangeListener(this);
    }

    public JazzHandsViewPager getViewPager() {
        return mJazzHandsViewPager;
    }

    public void addDecors(Decor... decors) {
        Collections.addAll(mDecors, decors);
        layoutDecors();
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return super.getChildDrawingOrder(childCount, i);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state == ViewPager.SCROLL_STATE_IDLE) {
//            layoutDecors();
        }
    }

    private void layoutDecors() {
        final int currentPage = mJazzHandsViewPager.getCurrentItem();
        for (Decor decor : mDecors) {
            if ((decor.startPage > currentPage || decor.endPage < currentPage)
                    && decor.isAdded) {
                decor.isAdded = false;
                removeView(decor.contentView);
            } else if (!decor.isAdded) {
                decor.isAdded = true;
                addView(decor.contentView);
            }
        }
    }

    public static class Decor {
        public final View contentView;

        public final int startPage;
        public final int endPage;

        final int layoutBehind;
        final int layoutAbove;

        boolean isAdded;

        public Decor(View contentView, int startPage, int endPage, int layoutBehind, int layoutAbove) {
            this.contentView = contentView;
            this.startPage = startPage;
            this.endPage = endPage;
            this.layoutBehind = layoutBehind;
            this.layoutAbove = layoutAbove;
        }

        public static class Builder {
            private View mContentView;

            private int mStartPage;
            private int mEndPage;

            private int mLayoutBehind;
            private int mLayoutAbove;

            public Builder setContentView(View contentView) {
                mContentView = contentView;
                return this;
            }

            public Builder setStartPage(int startPage) {
                mStartPage = startPage;
                return this;
            }

            public Builder setEndPage(int endPage) {
                mEndPage = endPage;
                return this;
            }

            public Builder setLayoutBehind(int layoutBehind) {
                mLayoutBehind = layoutBehind;
                return this;
            }

            public Builder setLayoutAbove(int layoutAbove) {
                mLayoutAbove = layoutAbove;
                return this;
            }

            public Decor build() {
                return new Decor(mContentView, mStartPage, mEndPage, mLayoutBehind, mLayoutAbove);
            }
        }

    }
}
