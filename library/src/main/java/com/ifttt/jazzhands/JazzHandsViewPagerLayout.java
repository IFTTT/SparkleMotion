package com.ifttt.jazzhands;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.TreeSet;

/**
 * Created by zhelu on 7/12/15.
 */
public class JazzHandsViewPagerLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private JazzHandsViewPager mJazzHandsViewPager;

    private final TreeSet<Decor> mDecors = new TreeSet<Decor>();

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
        boolean needChildDrawingOrder = false;
        for (Decor decor : decors) {
            decor.index = mDecors.size();
            mDecors.add(decor);

            needChildDrawingOrder = decor.layoutAbovePage >= 0 || decor.layoutBehindPage >= 0;
        }

        layoutDecors(mJazzHandsViewPager.getCurrentItem());

        if (needChildDrawingOrder) {
            // If there is request to put Decor above or below certain page, enable drawing order.
            setChildrenDrawingOrderEnabled(true);
        }
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return super.getChildDrawingOrder(childCount, i);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        layoutDecors(position + positionOffset);
    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
        if (state != ViewPager.SCROLL_STATE_IDLE) {

        }
    }

    private void layoutDecors(float currentPage) {
        for (Decor decor : mDecors) {
            if ((decor.startPage > currentPage || decor.endPage < currentPage)
                    && decor.isAdded) {
                decor.isAdded = false;
                removeView(decor.contentView);
            } else if (decor.startPage <= currentPage
                && decor.endPage >= currentPage
                && !decor.isAdded) {
                decor.isAdded = true;
                addView(decor.contentView);
            }
        }
    }

    public static class Decor implements Comparable<Decor> {
        public final View contentView;

        public final int startPage;
        public final int endPage;

        int layoutBehindPage = -1;
        int layoutAbovePage = -1;

        boolean isAdded;

        int index;

        public Decor(View contentView, int startPage, int endPage, int layoutBehind, int layoutAbove) {
            this.contentView = contentView;
            this.startPage = startPage;
            this.endPage = endPage;
            this.layoutBehindPage = layoutBehind;
            this.layoutAbovePage = layoutAbove;
        }

        @Override
        public int compareTo(@NonNull Decor another) {
            return index - another.index;
        }

        public static class Builder {
            private View mContentView;

            private int mStartPage;
            private int mEndPage;

            private int mLayoutBehindPage;
            private int mLayoutAbovePage;

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

            public Builder setLayoutBehindPage(int layoutBehindPage) {
                mLayoutBehindPage = layoutBehindPage;
                return this;
            }

            public Builder setLayoutAbovePage(int layoutAbovePage) {
                mLayoutAbovePage = layoutAbovePage;
                return this;
            }

            public Decor build() {
                return new Decor(mContentView, mStartPage, mEndPage, mLayoutBehindPage, mLayoutAbovePage);
            }
        }

    }
}
