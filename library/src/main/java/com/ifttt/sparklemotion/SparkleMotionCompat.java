package com.ifttt.sparklemotion;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Helper class for installing {@link SparkleAnimationPresenter} into a {@link ViewPager}, so
 * that any ViewPager can use SparkleMotion animations.
 * <p/>
 * The {@link SparkleAnimationPresenter} instance will be referenced by {@link View#setTag(int,
 * Object)} within the ViewPager, and a {@link android.support.v4.view.ViewPager.PageTransformer}
 * and a {@link android.support.v4.view.ViewPager.OnPageChangeListener} will be set as well
 * to provide functionality of SparkleMotion.
 * <p/>
 * Note: Once a SparkleAnimationPresenter is installed, if you would like to provide another
 * PageTransformer, you need to use
 * {@link #setPageTransformer(ViewPager, boolean, ViewPager.PageTransformer)} method, so
 * that the presenter is preserved.
 */
public final class SparkleMotionCompat {

    private SparkleMotionCompat() {
        throw new AssertionError("No instance");
    }

    /**
     * Given a ViewPager and a {@link SparkleAnimationPresenter}, sets a PageTransformer and a
     * OnPageChangeListener that provides functionality of the presenter.
     * The presenter will be referenced as a tag of the View.
     *
     * @param viewPager ViewPager instance.
     * @param reverseDrawingOrder Whether the ViewPager should reverse it child Views' drawing
     * order.
     * @param presenter SparkleAnimationPresenter instance to be installed.
     * @see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     */
    static void installAnimationPresenter(final ViewPager viewPager, boolean reverseDrawingOrder,
            final SparkleAnimationPresenter presenter) {
        Object tagObject = viewPager.getTag(R.id.presenter_id);
        if (tagObject != null && tagObject == presenter) {
            // If the presenter is the same as the one already in the ViewPager, return.
            return;
        }

        // Set PageTransformer
        ViewPager.PageTransformer transformer = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();
                float offset = pageWidth * -position;

                presenter.presentAnimations(page, position, offset);
            }
        };

        // Set OnPageChangeListener
        viewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                // Animate any Decor animations.
                presenter.presentDecorAnimations(position, positionOffset);
            }
        });

        viewPager.setPageTransformer(reverseDrawingOrder, transformer);

        viewPager.setTag(R.id.presenter_id, presenter);
    }

    /**
     * Convenient method for installing {@link SparkleAnimationPresenter}. A new
     * SparkleAnimationPresenter will be created and attached to the ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @param reverseDrawingOrder Whether the ViewPager should reverse it child Views' drawing
     * order.
     * @see {@link #installAnimationPresenter(ViewPager, boolean, SparkleAnimationPresenter)}
     * @see {@link #installAnimationPresenter(ViewPager)}
     */
    static void installAnimationPresenter(ViewPager viewPager, boolean reverseDrawingOrder) {
        final SparkleAnimationPresenter presenter = new SparkleAnimationPresenter();
        installAnimationPresenter(viewPager, reverseDrawingOrder, presenter);
    }

    /**
     * Convenient method for installing {@link SparkleAnimationPresenter} without reversing
     * ViewPager's child Views drawing order.
     * A new SparkleAnimationPresenter will be created and attached to the ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @see {@link #installAnimationPresenter(ViewPager, boolean, SparkleAnimationPresenter)}
     * @see {@link #installAnimationPresenter(ViewPager, boolean)}
     */
    static void installAnimationPresenter(ViewPager viewPager) {
        installAnimationPresenter(viewPager, false);
    }

    /**
     * Sets a {@link android.support.v4.view.ViewPager.PageTransformer} to the given ViewPager.
     * Using this method to set PageTransformer is required for the ViewPagers that already have
     * {@link SparkleAnimationPresenter} installed.
     *
     * @param viewPager ViewPager instance.
     * @param reversDrawingOrder Whether the ViewPager should reverse it child Views' drawing
     * order.
     * @param transformer PageTransformer instance.
     * @see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     * @see {@link android.support.v4.view.ViewPager.PageTransformer}
     */
    public static void setPageTransformer(ViewPager viewPager, boolean reversDrawingOrder,
            @Nullable final ViewPager.PageTransformer transformer) {
        Object tagObject = viewPager.getTag(R.id.presenter_id);
        if (tagObject == null || !(tagObject instanceof SparkleAnimationPresenter)) {
            viewPager.setPageTransformer(reversDrawingOrder, transformer);
            return;
        }

        final SparkleAnimationPresenter presenter = (SparkleAnimationPresenter) tagObject;
        ViewPager.PageTransformer transformerWrapper = new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                int pageWidth = page.getWidth();
                float offset = pageWidth * -position;

                presenter.presentAnimations(page, position, offset);
                if (transformer != null) {
                    transformer.transformPage(page, position);
                }
            }
        };

        viewPager.setPageTransformer(reversDrawingOrder, transformerWrapper);
    }

    /**
     * Gets the {@link SparkleAnimationPresenter} instance attached to the given ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @return SparkleAnimationPresenter instance if set, or null.
     */
    static SparkleAnimationPresenter getAnimationPresenter(ViewPager viewPager) {
        Object tagObject = viewPager.getTag(R.id.presenter_id);
        if (tagObject == null || !(tagObject instanceof SparkleAnimationPresenter)) {
            return null;
        }

        return (SparkleAnimationPresenter) tagObject;
    }

    /**
     * Convenient method to check whether {@link SparkleAnimationPresenter} is attached to the
     * given ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @return True if there is a SparkleAnimationPresenter attached, false otherwise.
     */
    static boolean hasPresenter(ViewPager viewPager) {
        return getAnimationPresenter(viewPager) != null;
    }
}
