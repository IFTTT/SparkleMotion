package com.ifttt.jazzhands;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import com.ifttt.jazzhands.animations.JazzHandsAnimationPresenter;

/**
 * Extending {@link android.support.v4.view.ViewPager} to reference a {@link JazzHandsAnimationPresenter}
 * and run animations based on its scroll X. This subclass contains a default {@link android.support.v4.view.ViewPager.PageTransformer}
 * that passes the page View and its position to the JazzHandsAnimationPresenter, which will then run all
 * animations stored.
 */
public class JazzHandsViewPager extends android.support.v4.view.ViewPager {

    public static final int MIM_OFFSCREEN_LIMIT = 2;

    /**
     * {@link JazzHandsAnimationPresenter} instance that stores all animations within this ViewPager.
     */
    private JazzHandsAnimationPresenter mJazzHandsAnimationPresenter;

    private boolean mReverseDrawingOrder;

    /**
     * An index of the View to be brought to front regardless the drawing order. Used by {@link #bringChildViewToFront(int)}.
     */
    private int mViewIndexBroughtToFront = -1;

    private int mCurrentScrollPage;

    public JazzHandsViewPager(Context context) {
        super(context);
    }

    public JazzHandsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setJazzHandsAnimationPresenter(JazzHandsAnimationPresenter director) {
        setJazzHandsAnimationPresenter(director, false);
    }

    public void setJazzHandsAnimationPresenter(JazzHandsAnimationPresenter presenter, boolean reverseDrawingOrder) {
        mJazzHandsAnimationPresenter = presenter;
        mJazzHandsAnimationPresenter.setViewPagerId(getId());

        setPageTransformer(reverseDrawingOrder, null);
    }

    public JazzHandsAnimationPresenter getJazzHandsAnimationPresenter() {
        return mJazzHandsAnimationPresenter;
    }

    public boolean hasPresenter() {
        return mJazzHandsAnimationPresenter != null;
    }

    /**
     * Bring a page within ViewPager to front, which means this view will be drawn last. All of the other
     * pages will appear below it.
     * <p/>
     * Right now there is a limitation of this method: to use it, {@link #setOffscreenPageLimit(int)} must be used
     * and the ViewPager have to contain all pages.
     *
     * @param index Index of the child View to be brought to front.
     */
    public void bringChildViewToFront(int index) {
        if (getAdapter() == null) {
            throw new IllegalStateException("Adapter is null");
        }

        // Set offscreen page limit so that page will not be destroyed if it is offscreen.
        setOffscreenPageLimit(
                Math.max(mJazzHandsAnimationPresenter.getMaxCrossPageAnimationPages(), MIM_OFFSCREEN_LIMIT));

        if (!isWithinBoundary(index)) {
            throw new IllegalArgumentException("Invalid index: [" + index + "], exceeding offscreen limit: " + getOffscreenPageLimit());
        }

        mViewIndexBroughtToFront = index;
    }

    private boolean isWithinBoundary(int index) {
        return index >= 0 && index < getAdapter().getCount();
    }

    @Override
    public void setOffscreenPageLimit(int limit) {
        // If we need to bring a child view to front, a minimum of 2 offscreen pages is required.
        super.setOffscreenPageLimit(mViewIndexBroughtToFront >= 0 ?
                Math.max(mJazzHandsAnimationPresenter.getMaxCrossPageAnimationPages(), MIM_OFFSCREEN_LIMIT) : limit);
    }

    /**
     * Convenient method to set {@link android.support.v4.view.ViewPager.PageTransformer} with pre-defined
     * {@link #mReverseDrawingOrder}.
     *
     * @param transformer PageTransformer to use in this ViewPager.
     */
    public void setPageTransformer(PageTransformer transformer) {
        this.setPageTransformer(mReverseDrawingOrder, transformer);
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, final PageTransformer transformer) {
        mReverseDrawingOrder = reverseDrawingOrder;

        if (mJazzHandsAnimationPresenter != null) {
            // Set a default PageTransformer to play animations stored in the presenter. The default
            // PageTransformer will wrap the one passed into this method.
            PageTransformer jazzHandsTransformer = new PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    if (page.getLayerType() != View.LAYER_TYPE_NONE
                            && mJazzHandsAnimationPresenter.hasCrossPageAnimation()) {
                        // Layer type has to be NONE if we want to play cross page animations.
                        page.setLayerType(View.LAYER_TYPE_NONE, null);
                    }

                    int pageWidth = page.getWidth();
                    float offset = pageWidth * -position;

                    mJazzHandsAnimationPresenter
                            .presentAnimations(page, position, offset);

                    if (transformer != null) {
                        transformer.transformPage(page, position);
                    }
                }
            };

            super.setPageTransformer(reverseDrawingOrder, jazzHandsTransformer);
        } else {
            super.setPageTransformer(reverseDrawingOrder, transformer);
        }
    }

    @Override
    protected void onPageScrolled(int position, float offset, int offsetPixels) {
        mCurrentScrollPage = position;
        if (mJazzHandsAnimationPresenter != null) {
            mJazzHandsAnimationPresenter.setCurrentPage(position);
        }
        super.onPageScrolled(position, offset, offsetPixels);
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (mViewIndexBroughtToFront >= 0) {
            int index = getSortedChildDrawingOrder(childCount, i);
            if (index >= 0) {
                return index;
            }
        }

        return super.getChildDrawingOrder(childCount, i);
    }


    /**
     * Get the re-ordered child drawing order for a certain iteration. This method is used if {@link #bringChildViewToFront(int)}
     * is used and a View is to be brought to front.
     *
     * @param childCount Child count of ViewPager.
     * @param i          Drawing iteration.
     * @return View index to be drawn in this iteration.
     */
    private int getSortedChildDrawingOrder(int childCount, int i) {
        int curIndex = getCurrentItemDrawingIteration(childCount);
        int offscreenLimit = getOffscreenPageLimit();
        int frontIndex;
        if (mCurrentScrollPage == mViewIndexBroughtToFront) {
            frontIndex = curIndex;
        } else if (mCurrentScrollPage + offscreenLimit == mViewIndexBroughtToFront) {
            frontIndex = childCount - 1;
        } else if (mCurrentScrollPage - offscreenLimit == mViewIndexBroughtToFront) {
            frontIndex = 0;
        } else if (mCurrentScrollPage + offscreenLimit > mViewIndexBroughtToFront
                && mCurrentScrollPage < mViewIndexBroughtToFront) {
            frontIndex = curIndex + (mViewIndexBroughtToFront - mCurrentScrollPage);
        } else if (mCurrentScrollPage - offscreenLimit < mViewIndexBroughtToFront
                && mCurrentScrollPage > mViewIndexBroughtToFront) {
            frontIndex = curIndex - (mCurrentScrollPage - mViewIndexBroughtToFront);
        } else {
            frontIndex = -1;
        }

        return getAdjustedChildDrawingOrder(childCount, i, frontIndex);
    }

    /**
     * Get the drawing iteration for the current page.
     *
     * @param childCount Child count of ViewPager
     * @return The drawing order of the current page.
     */
    private int getCurrentItemDrawingIteration(int childCount) {
        final int curIndex = mCurrentScrollPage;
        int offscreenLimit = getOffscreenPageLimit();
        if (curIndex - offscreenLimit < 0) {
            return curIndex;
        }

        if (getAdapter().getCount() - 1 - curIndex < offscreenLimit) {
            return childCount - 1 - (getAdapter().getCount() - curIndex - 1);
        }

        return childCount / 2;
    }

    /**
     * Get child drawing order regarding the page to be brought to front. The page to be brought to
     * front will be drawn in the last iteration, other Views will be drawn based on {@link #mReverseDrawingOrder}/
     *
     * @param childCount Child count of the ViewPager.
     * @param i          Drawing iteration.
     * @param frontIndex Index of the page to be brought to front.
     * @return Adjusted child index to be drawn in this iteration.
     */
    private int getAdjustedChildDrawingOrder(int childCount, int i, int frontIndex) {
        if (frontIndex >= childCount || frontIndex < 0) {
            return -1;
        }

        if (i == childCount - 1) {
            int realIndex = mReverseDrawingOrder ? childCount - frontIndex - 1 : frontIndex;
            return super.getChildDrawingOrder(childCount, realIndex);
        } else if (mReverseDrawingOrder && (childCount - i - 1) <= frontIndex) {
            return super.getChildDrawingOrder(childCount, i + 1);
        } else if (!mReverseDrawingOrder && i >= frontIndex) {
            return super.getChildDrawingOrder(childCount, i + 1);
        }
        return -1;
    }
}
