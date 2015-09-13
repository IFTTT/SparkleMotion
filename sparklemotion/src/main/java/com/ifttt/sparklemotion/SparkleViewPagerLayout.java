package com.ifttt.sparklemotion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A wrapper FrameLayout containing a {@link ViewPager}. This class supports adding
 * {@link Decor} to the ViewPager, which can be animated across pages.
 * <p/>
 * A Decor of the ViewPager is only for animation purpose, which means if there's no animation
 * associated with it, it will stay at the original position when it is added to the parent.
 */
public class SparkleViewPagerLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private ViewPager mViewPager;

    /**
     * Index of the ViewPager within this layout.
     */
    private int mViewPagerIndex;

    private final ArrayList<Decor> mDecors = new ArrayList<Decor>();

    public SparkleViewPagerLayout(Context context) {
        super(context);
    }

    public SparkleViewPagerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SparkleViewPagerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        if (child instanceof ViewPager) {
            setViewPager((ViewPager) child, index);
        }

        super.addView(child, index, params);
    }

    /**
     * Setup a ViewPager within this layout, so that we can use it to run animations.
     *
     * @param viewPager ViewPager object being added to this layout.
     * @param index     Index of the ViewPager being added to this layout.
     */
    private void setViewPager(@NonNull ViewPager viewPager, int index) {
        if (mViewPager != null) {
            throw new IllegalStateException("SparkleViewPagerLayout already has a ViewPager set.");
        }

        if (!SparkleMotionCompat.hasPresenter(viewPager)) {
            SparkleMotionCompat.installAnimationPresenter(viewPager);
        }

        mViewPagerIndex = index < 0 ? getChildCount() : index;

        mViewPager = viewPager;
        mViewPager.addOnPageChangeListener(this);
    }

    /**
     * Return the {@link ViewPager} used in this layout.
     *
     * @return ViewPager object.
     */
    public ViewPager getViewPager() {
        return mViewPager;
    }

    /**
     * Add {@link Decor} into this layout. Decor will be added to the
     * layout based on its start page and end page. Calling this method will also enables children
     * drawing order, which
     * follows the order of the parameters, e.g the first Decor will be drawn first.
     *
     * @param decor Decor object to be added to this layout.
     * @throws IllegalStateException when ViewPager is not set in this layout.
     */
    @SuppressWarnings("unused")
    public void addDecor(Decor decor) {
        if (mViewPager == null) {
            throw new IllegalStateException(
                    "ViewPager is not found in JazzHandsViewPagerLayout, please call setViewPager() first");
        }

        if (decor == null) {
            return;
        }

        // Make sure there is no duplicate.
        if (mDecors.contains(decor)) {
            return;
        }

        mDecors.add(decor);

        // If slide out attribute is true, build a TranslationAnimation for the last page to
        // change the translation X when the ViewPager is scrolling.
        if (decor.slideOut) {
            SparkleAnimationPresenter presenter = SparkleMotionCompat.getAnimationPresenter(mViewPager);
            if (presenter != null) {
                presenter.addAnimation(decor, new SlideOutAnimation(Page.singlePage(decor.endPage)));
            }
        }

        if (decor.layoutBehindViewPage) {
            addView(decor.contentView, mViewPagerIndex);
            mViewPagerIndex++;
        } else {
            addView(decor.contentView);
        }

        layoutDecors(mViewPager.getCurrentItem(), 0);
    }

    /**
     * Remove Decor objects from this layout.
     *
     * @param decor Decor objects to be removed.
     */
    @SuppressWarnings("unused")
    public void removeDecor(Decor decor) {
        int indexOfRemoved = mDecors.indexOf(decor);
        if (indexOfRemoved < 0) {
            throw new IllegalArgumentException("Decor is not added to SparkleViewPagerLayout");
        }

        mDecors.remove(decor);
        removeDecorView(decor);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (positionOffset >= 0) {
            layoutDecors(position, positionOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        enableLayer(state != ViewPager.SCROLL_STATE_IDLE);
    }

    /**
     * If the ViewPager is scrolling and there are Decors that are running animations, enable their
     * content Views' hardware layer. Otherwise, switch back to no layer.
     *
     * @param enable Whether or not hardware layer should be used for Decor content views.
     */
    private void enableLayer(boolean enable) {
        for (Decor decor : mDecors) {
            if (enable && decor.contentView.getLayerType() != LAYER_TYPE_HARDWARE) {
                decor.contentView.setLayerType(LAYER_TYPE_HARDWARE, null);
            } else if (!enable && decor.contentView.getLayerType() != LAYER_TYPE_NONE) {
                decor.contentView.setLayerType(LAYER_TYPE_NONE, null);
            }
        }
    }

    /**
     * Based on the <code>startPage</code>, <code>endPage</code> and <code>layoutBehindViewPager</code>
     * from {@link Decor}, show or hide Decors to this FrameLayout.
     *
     * @param currentPage Currently displayed ViewPager page.
     * @param offset      ViewPager scrolling offset.
     */
    private void layoutDecors(int currentPage, float offset) {
        float currentPageOffset = currentPage + offset;
        int decorsSize = mDecors.size();
        for (int i = 0; i < decorsSize; i++) {
            Decor decor = mDecors.get(i);
            if (decor.contentView.getVisibility() == VISIBLE && decor.startPage != Page.ALL_PAGES) {
                int endPage = decor.slideOut ? decor.endPage + 1 : decor.endPage;
                if (decor.startPage > currentPageOffset || endPage < currentPageOffset) {
                    decor.contentView.setVisibility(INVISIBLE);
                }
            } else if (decor.startPage == Page.ALL_PAGES
                    || (decor.endPage + 1 >= currentPageOffset && decor.slideOut)
                    || (decor.startPage <= currentPageOffset && decor.endPage >= currentPageOffset)) {
                decor.contentView.setVisibility(VISIBLE);

            }
        }
    }

    private void removeDecorView(Decor decor) {
        removeView(decor.contentView);

        if (decor.layoutBehindViewPage) {
            mViewPagerIndex--;
        }
    }
}
