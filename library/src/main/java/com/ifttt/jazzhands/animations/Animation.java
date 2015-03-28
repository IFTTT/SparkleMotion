package com.ifttt.jazzhands.animations;

import android.view.View;
import android.view.animation.Interpolator;

/**
 * Abstract class for running JazzHands animation. This class contains all common information about the
 * animation to be run on ViewPager pages, with an abstract method {@link #onAnimate(View, float, float)} to be
 * implemented so that the View properties can be changed during ViewPager scrolling.
 */
public abstract class Animation {

    public static final String PAGE_ROOT_FLAG = "JazzHands_PageRoot";

    /**
     * A boolean flag indicating whether this animation is relative to the parent page. If so, the animated
     * view will still be scrolled to the side along with it's parent page while animating. If not, the view
     * will be animated based on the screen and will ignore the moving parent page. This means that the view
     * can potentially be animated across different pages.
     */
    protected boolean absolute;

    protected int pageStart;
    protected int pageEnd;

    /**
     * Adjustment to page fraction taking animating pages into account. If an animation is going to run
     * cross multiple pages, the progress will be evenly distributed to the pages.
     */
    protected float fractionAdjustment;

    private Interpolator mInterpolator;

    private AnimationListener mAnimationListener;

    /**
     * Base constructor of the class, accepting common information about the animation to this instance.
     *
     * @param start    Page index that this animation should start.
     * @param end      Page index that this animation should stop.
     * @param absolute Whether or not this animation is absolute to the device screen.
     */
    public Animation(
            int start, int end, boolean absolute) {
        this.absolute = absolute;
        this.pageStart = start;
        this.pageEnd = end;

        fractionAdjustment = (float) Math.max((pageEnd - pageStart), 1);
    }

    public void setInterpolator(Interpolator interpolator) {
        mInterpolator = interpolator;
    }

    public Interpolator getInterpolator() {
        return mInterpolator;
    }

    /**
     * Main method for animating Views within the pages.
     *
     * @param v        View to be animated.
     * @param fraction Fraction of the ViewPager scrolling, this is also the progression of the animation.
     * @param offset   Page width offset.
     */
    void animate(View v, float fraction, float offset) {
        Interpolator interpolator = getInterpolator();
        if (interpolator != null) {
            fraction = interpolator.getInterpolation(fraction);
        }

        if (absolute && fraction > pageStart) {
            fraction -= pageStart;
        }
        fraction = fraction / fractionAdjustment;

        if (mAnimationListener != null) {
            mAnimationListener.onAnimationRunning(fraction);
        }

        onAnimate(v, fraction, offset);
    }

    /**
     * Abstract method to be implemented to change View properties. Implement this method to provide custom
     * animations to the target View.
     *
     * @param v        View to be animated.
     * @param fraction Fraction of the ViewPager scrolling, this is also the progression of the animation.
     * @param offset   Page width offset.
     */
    protected abstract void onAnimate(View v, float fraction, float offset);


    /**
     * Check the current page with {@link JazzHandsAnimationPresenter} and see if it is within {@link #pageStart}
     * and {@link #pageEnd}.
     *
     * @param currentPage Current page in ViewPager where the scroll starts.
     * @return True if the animation should run, false otherwise.
     */
    protected boolean shouldAnimate(int currentPage) {
        return pageStart <= currentPage
                && pageEnd >= currentPage;
    }

    public void setAnimationListener(AnimationListener listener) {
        mAnimationListener = listener;
    }

    /**
     * Animation callback for
     */
    public interface AnimationListener {
        void onAnimationRunning(float fraction);
    }

}
