package com.ifttt.jazzhands;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * A wrapper FrameLayout containing a {@link JazzHandsViewPager}. This class supports adding
 * {@link com.ifttt.jazzhands.JazzHandsViewPagerLayout.Decor} to the ViewPager, which can be animated across pages.
 * <p/>
 * A Decor of the ViewPager is only for animation purpose, which means if there's no animation associated with it,
 * it will stay at the original position when it is added to the parent.
 */
public class JazzHandsViewPagerLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private JazzHandsViewPager mJazzHandsViewPager;

    private final ArrayList<Decor> mDecors = new ArrayList<Decor>();

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
        for (Decor decor : decors) {
            if (mDecors.contains(decor)) {
                continue;
            }

            mDecors.add(decor);
        }
        layoutDecors(mJazzHandsViewPager.getCurrentItem());

    }

    public void removeDecors(Decor... decors) {
        for (Decor decor : decors) {
            mDecors.remove(decor);
        }
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
        enableLayer(state != ViewPager.SCROLL_STATE_IDLE);
    }

    /**
     * If the ViewPager is scrolling and there are Decors that are running animations, enable their content Views'
     * hardware layer. Otherwise, switch back to no layer.
     *
     * @param enable    Whether or not hardware layer should be used for Decor content views.
     */
    private void enableLayer(boolean enable) {
        for (Decor decor : mDecors) {
            if (!decor.isAdded) {
                continue;
            }

            if (enable && decor.contentView.getLayerType() != LAYER_TYPE_HARDWARE) {
                decor.contentView.setLayerType(LAYER_TYPE_HARDWARE, null);
            } else if (!enable && decor.contentView.getLayerType() != LAYER_TYPE_NONE) {
                decor.contentView.setLayerType(LAYER_TYPE_NONE, null);
            }
        }
    }

    /**
     * Based on the {@code startPage}, {@code endPage} and {@code layoutBehindViewPager} from
     * {@link com.ifttt.jazzhands.JazzHandsViewPagerLayout.Decor}, add or remove Decors to this FrameLayout.
     *
     * @param currentPage Currently displayed ViewPager page.
     */
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

                int index = decor.layoutBehindViewPage ? 0 : -1;
                addView(decor.contentView, index);
            }
        }
    }

    /**
     * An animation decoration of the {@link JazzHandsViewPager}. A Decor will respond to the scrolling of the ViewPager
     * and run animations based on it.
     */
    public static class Decor implements Comparable<Decor> {
        public final View contentView;

        public final int startPage;
        public final int endPage;

        boolean layoutBehindViewPage = false;

        boolean isAdded;

        int index;

        public Decor(@NonNull View contentView, int startPage, int endPage, boolean layoutBehind) {
            this.contentView = contentView;
            this.startPage = startPage;
            this.endPage = endPage;
            this.layoutBehindViewPage = layoutBehind;
        }

        @Override
        public int compareTo(@NonNull Decor another) {
            if (layoutBehindViewPage && !another.layoutBehindViewPage) {
                return -1;
            }

            return index - another.index;
        }

        public static class Builder {
            private View mContentView;

            private int mStartPage;
            private int mEndPage;

            private boolean mLayoutBehindViewPage;

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

            public Builder behindViewPage() {
                mLayoutBehindViewPage = true;
                return this;
            }

            public Decor build() {
                if (mStartPage > mEndPage) {
                    throw new IllegalArgumentException("Start page is larger than end page");
                }

                return new Decor(mContentView, mStartPage, mEndPage, mLayoutBehindViewPage);
            }
        }

    }
}
