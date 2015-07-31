package com.ifttt.jazzhands;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Extending {@link android.support.v4.view.ViewPager} to reference a {@link JazzHandsAnimationPresenter}
 * and run animations based on its scroll X. This subclass contains a default {@link
 * android.support.v4.view.ViewPager.PageTransformer}
 * that passes the page View and its position to the JazzHandsAnimationPresenter, which will then run all
 * animations stored.
 */
public class JazzHandsViewPager extends android.support.v4.view.ViewPager {

    /**
     * {@link JazzHandsAnimationPresenter} instance that stores all animations within this ViewPager.
     */
    private JazzHandsAnimationPresenter mJazzHandsAnimationPresenter;

    private boolean mReverseDrawingOrder;

    public JazzHandsViewPager(Context context) {
        super(context);
    }

    public JazzHandsViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Convenient method of {@link #setJazzHandsAnimationPresenter(JazzHandsAnimationPresenter, boolean)} without
     * reversing page drawing order.
     *
     * @param presenter JazzHandsAnimationPresenter object to set.
     */
    void setJazzHandsAnimationPresenter(JazzHandsAnimationPresenter presenter) {
        setJazzHandsAnimationPresenter(presenter, false);
    }

    /**
     * Install a {@link JazzHandsAnimationPresenter} into this ViewPager to enable animations. Calling this method will
     * set a default {@link android.support.v4.view.ViewPager.PageTransformer} to the ViewPager, which runs the
     * animations in the presenter.
     *
     * @param presenter           JazzHandsAnimationPresenter object to set.
     * @param reverseDrawingOrder Whether the ViewPager should reverse the drawing order of the pages.
     */
    void setJazzHandsAnimationPresenter(JazzHandsAnimationPresenter presenter, boolean reverseDrawingOrder) {
        mJazzHandsAnimationPresenter = presenter;

        setPageTransformer(reverseDrawingOrder, null);
    }

    JazzHandsAnimationPresenter getJazzHandsAnimationPresenter() {
        return mJazzHandsAnimationPresenter;
    }

    boolean hasPresenter() {
        return mJazzHandsAnimationPresenter != null;
    }

    @Override
    public void setPageTransformer(boolean reverseDrawingOrder, final PageTransformer transformer) {
        if (mReverseDrawingOrder != reverseDrawingOrder) {
            mReverseDrawingOrder = reverseDrawingOrder;
        }

        if (mJazzHandsAnimationPresenter != null) {
            // Set a default PageTransformer to play animations stored in the presenter. The default
            // PageTransformer will wrap the one passed into this method.
            PageTransformer jazzHandsTransformer = new PageTransformer() {
                @Override
                public void transformPage(View page, float position) {
                    int pageWidth = page.getWidth();
                    float offset = pageWidth * -position;

                    mJazzHandsAnimationPresenter.presentAnimations(page, position, offset);

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
        if (mJazzHandsAnimationPresenter != null) {
            mJazzHandsAnimationPresenter.setCurrentPage(position);
            // Animate any Decor animations.
            mJazzHandsAnimationPresenter.presentDecorAnimations(position, offset);
        }

        super.onPageScrolled(position, offset, offsetPixels);
    }
}
