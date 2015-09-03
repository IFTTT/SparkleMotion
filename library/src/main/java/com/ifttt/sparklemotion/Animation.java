package com.ifttt.sparklemotion;

import android.view.View;
import android.view.animation.Interpolator;

/**
 * Abstract class for running SparkleMotion animation. This class contains all common information about
 * the animation to be run on ViewPager pages, with an abstract method
 * {@link #onAnimate(View, float, float)} to be implemented so that the View properties can be
 * changed during ViewPager scrolling.
 */
public abstract class Animation {

    /**
     * Flag used to indicate that this animation should be run for every page.
     */
    public static final int ALL_PAGES = -1;

    public static final int ANIMATION_ID_PAGE = -2;

    protected int pageStart;
    protected int pageEnd;

    /**
     * Adjustment to page fraction taking animating pages into account. If an animation is going to
     * run cross multiple pages, the progress will be evenly distributed to the pages.
     */
    private float mFractionAdjustment;

    private Interpolator mInterpolator;

    private AnimationListener mAnimationListener;

    /**
     * Currently animated offset. Used to determine whether the animation is finished.
     */
    private float mCurrentOffset;

    /**
     * Convenient constructor that has default page start and end set to {@link #ALL_PAGES}.
     */
    public Animation() {
        this(ALL_PAGES);
    }

    /**
     * Convenient constructor that sets the page start and end to be the same page.
     *
     * @param page Page index that this animation should run.
     */
    public Animation(int page) {
        this(page, page);
    }

    /**
     * Base constructor of the class, accepting common information about the animation to this
     * instance.
     *
     * @param start Page index that this animation should start.
     * @param end Page index that this animation should stop.
     */
    public Animation(int start, int end) {
        this.pageStart = start;
        this.pageEnd = end;

        mFractionAdjustment = (float) Math.max((pageEnd - pageStart), 1);
    }

    public final void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * Main method for animating Views within the pages.
     *
     * @param v View to be animated.
     * @param offset Fraction of the ViewPager scrolling, this is also the progression of the
     * animation.
     * @param offsetInPixel Page width offset.
     */
    void animate(View v, float offset, float offsetInPixel) {
        if (mInterpolator != null) {
            offset = mInterpolator.getInterpolation(offset);
        }

        if (pageStart != ALL_PAGES && pageEnd != ALL_PAGES) {
            if (offset > pageStart) {
                offset -= pageStart;
            }

            offset = offset / mFractionAdjustment;
        }

        mCurrentOffset = offset;

        if (offset < -1) {
            onAnimateOffScreenLeft(v, offset, offsetInPixel);
        } else if (offset <= 1) {
            onAnimate(v, offset, offsetInPixel);
        } else {
            onAnimateOffScreenRight(v, offset, offsetInPixel);
        }

        if (mAnimationListener != null) {
            mAnimationListener.onAnimationRunning(v, offset);
        }
    }

    /**
     * Return the current offset of this animation.
     */
    float getCurrentOffset() {
        return mCurrentOffset;
    }

    /**
     * Abstract method to be implemented to change View properties. Implement this method to
     * provide custom animations to the target View. This method will be called when the page is
     * still visible within the ViewPager. Range [-1, 0] means the page is currently scrolling
     * to the left of the window, and [0, 1] means the page is currently scrolling to the right of
     * the window.
     *
     * @param v View being animated.
     * @param offset Fraction of the ViewPager scrolling, this is also the progression of
     * the
     * animation, the
     * range of the offset is [-1, 1].
     * @param offsetInPixel Page width offset.
     */
    public abstract void onAnimate(View v, float offset, float offsetInPixel);

    /**
     * Called when the animation is running when the View is off screen and is to the left of the
     * current screen.
     *
     * @param v View being animated.
     * @param offset Fraction of the ViewPager scrolling, this is also the progression of
     * the
     * animation.
     * @param offsetInPixel Page width offset.
     */
    @SuppressWarnings("unused")
    public void onAnimateOffScreenLeft(View v, float offset, float offsetInPixel) {
    }

    /**
     * Called when the animation is running when the View is off screen and is to the right of the
     * current screen.
     *
     * @param v View being animated.
     * @param offset Fraction of the ViewPager scrolling, this is also the progression of
     * the
     * animation.
     * @param offsetInPixel Page width offset.
     */
    @SuppressWarnings("unused")
    public void onAnimateOffScreenRight(View v, float offset, float offsetInPixel) {
    }

    /**
     * Check the current page with {@link SparkleAnimationPresenter} and see if it is within
     * {@link #pageStart} and {@link #pageEnd}.
     *
     * @param currentPage Current page in ViewPager where the scroll starts.
     * @return True if the animation should run, false otherwise.
     */
    boolean shouldAnimate(int currentPage) {
        return (pageStart == pageEnd && pageStart == Animation.ALL_PAGES)
                || pageStart <= currentPage && pageEnd >= currentPage;
    }

    public final void setAnimationListener(AnimationListener listener) {
        mAnimationListener = listener;
    }

    /**
     * Animation callback interface for external use.
     */
    public interface AnimationListener {
        /**
         * Called when the animation is running.
         *
         * @param view View being animated.
         * @param fraction Current fraction of the animation.
         */
        void onAnimationRunning(View view, float fraction);
    }
}
