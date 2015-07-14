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

    public void addDecorViewAnimations(Decor decor, Animation... animations) {
        mDecors.add(decor);
        mJazzHandsViewPager.getJazzHandsAnimationPresenter().addAnimation(decor.contentView, animations);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        return super.getChildDrawingOrder(childCount, i);
    }

    public static class Decor {
        final View contentView;

        final int startPage;
        final int endPage;

        final int layoutBehind;
        final int layoutAbove;

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
        }

    }
}
