package com.ifttt.jazzhands;

import android.view.View;
import android.view.animation.Interpolator;

/**
 * Abstract class for running JazzHands animation. This class contains all common information about the
 * animation to be run on ViewPager pages, with an abstract method {@link #onAnimate(View, float, float)} to be
 * implemented so that the View properties can be changed during ViewPager scrolling.
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
     * Adjustment to page fraction taking animating pages into account. If an animation is going to run
     * cross multiple pages, the progress will be evenly distributed to the pages.
     */
    private float fractionAdjustment;

    private Interpolator mInterpolator;

    private AnimationListener mAnimationListener;

    /**
     * Base constructor of the class, accepting common information about the animation to this instance.
     *
     * @param start Page index that this animation should start.
     * @param end   Page index that this animation should stop.
     */
    public Animation(
            int start, int end) {
        this.pageStart = start;
        this.pageEnd = end;

        fractionAdjustment = (float) Math.max((pageEnd - pageStart), 1);
    }

    public final void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    /**
     * Main method for animating Views within the pages.
     *
     * @param v             View to be animated.
     * @param offset        Fraction of the ViewPager scrolling, this is also the progression of the animation.
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

            offset = offset / fractionAdjustment;
        }

        if (mAnimationListener != null) {
            mAnimationListener.onAnimationRunning(offset);
        }

        if (offset < -1) {
            onAnimateOffScreenLeft(v, offset, offsetInPixel);
        } else if (offset <= 1) {
            onAnimate(v, offset, offsetInPixel);
        } else {
            onAnimateOffScreenRight(v, offset, offsetInPixel);
        }

    }

    /**
     * Abstract method to be implemented to change View properties. Implement this method to provide custom
     * animations to the target View.
     *
     * @param v             View being animated.
     * @param offset        Fraction of the ViewPager scrolling, this is also the progression of the animation, the
     *                      range of the offset is [-1, 1].
     * @param offsetInPixel Page width offset.
     */
    protected abstract void onAnimate(View v, float offset, float offsetInPixel);

    /**
     * Called when the animation is running when the View is off screen and is to the left of the current screen.
     *
     * @param v             View being animated.
     * @param offset        Fraction of the ViewPager scrolling, this is also the progression of the animation.
     * @param offsetInPixel Page width offset.
     */
    @SuppressWarnings("unused")
    protected void onAnimateOffScreenLeft(View v, float offset, float offsetInPixel) {
    }

    /**
     * Called when the animation is running when the View is off screen and is to the right of the current screen.
     *
     * @param v             View being animated.
     * @param offset        Fraction of the ViewPager scrolling, this is also the progression of the animation.
     * @param offsetInPixel Page width offset.
     */
    @SuppressWarnings("unused")
    protected void onAnimateOffScreenRight(View v, float offset, float offsetInPixel) {
    }


    /**
     * Check the current page with {@link JazzHandsAnimationPresenter} and see if it is within {@link #pageStart}
     * and {@link #pageEnd}.
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
         * @param fraction Current fraction of the animation.
         */
        void onAnimationRunning(float fraction);
    }

}
