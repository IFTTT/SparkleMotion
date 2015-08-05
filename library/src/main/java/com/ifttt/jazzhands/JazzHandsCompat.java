package com.ifttt.jazzhands;

import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Helper class for installing {@link JazzHandsAnimationPresenter} into a {@link ViewPager}, so
 * that any ViewPager can use JazzHands animations.
 * <p/>
 * The {@link JazzHandsAnimationPresenter} instance will be referenced by {@link View#setTag(int,
 * Object)} within the ViewPager, and a {@link android.support.v4.view.ViewPager.PageTransformer}
 * and a {@link android.support.v4.view.ViewPager.OnPageChangeListener} will be set as well
 * to provide functionality of JazzHands.
 * <p/>
 * Note: Once a JazzHandsAnimationPresenter is installed, if you would like to provide another
 * PageTransformer, you need to use
 * {@link #setPageTransformer(ViewPager, boolean, ViewPager.PageTransformer)} method, so
 * that the presenter is preserved.
 */
public final class JazzHandsCompat {

    private JazzHandsCompat() {
        throw new AssertionError("No instance");
    }

    /**
     * Given a ViewPager and a {@link JazzHandsAnimationPresenter}, sets a PageTransformer and a
     * OnPageChangeListener that provides functionality of the presenter.
     * The presenter will be referenced as a tag of the View.
     *
     * @param viewPager ViewPager instance.
     * @param reverseDrawingOrder Whether the ViewPager should reverse it child Views' drawing
     * order.
     * @param presenter JazzHandsAnimationPresenter instance to be installed.
     * @see {@link ViewPager#setPageTransformer(boolean, ViewPager.PageTransformer)}
     */
    static void installJazzHandsPresenter(ViewPager viewPager, boolean reverseDrawingOrder,
            final JazzHandsAnimationPresenter presenter) {
        Object tagObject = viewPager.getTag(R.id.presenter_id);
        if (tagObject != null && tagObject == presenter) {
            // If the presenter is the same as the one already in the ViewPager, return.
            return;
        }

        // Set PageTransformer
        ViewPager.PageTransformer jazzHandsTransformer = new ViewPager.PageTransformer() {
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
            public void onPageScrolled(int position, float positionOffset,
                    int positionOffsetPixels) {
                presenter.setCurrentPage(position);
                // Animate any Decor animations.
                presenter.presentDecorAnimations(position, positionOffset);
            }
        });

        viewPager.setPageTransformer(reverseDrawingOrder, jazzHandsTransformer);

        viewPager.setTag(R.id.presenter_id, presenter);
    }

    /**
     * Convenient method for installing {@link JazzHandsAnimationPresenter}. A new
     * JazzHandsAnimationPresenter will be created and attached to the ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @param reverseDrawingOrder Whether the ViewPager should reverse it child Views' drawing
     * order.
     * @see {@link #installJazzHandsPresenter(ViewPager, boolean, JazzHandsAnimationPresenter)}
     * @see {@link #installJazzHandsPresenter(ViewPager)}
     */
    public static void installJazzHandsPresenter(ViewPager viewPager, boolean reverseDrawingOrder) {
        final JazzHandsAnimationPresenter presenter = new JazzHandsAnimationPresenter();
        installJazzHandsPresenter(viewPager, reverseDrawingOrder, presenter);
    }

    /**
     * Convenient method for installing {@link JazzHandsAnimationPresenter} without reversing
     * ViewPager's child Views drawing order.
     * A new JazzHandsAnimationPresenter will be created and attached to the ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @see {@link #installJazzHandsPresenter(ViewPager, boolean, JazzHandsAnimationPresenter)}
     * @see {@link #installJazzHandsPresenter(ViewPager, boolean)}
     */
    public static void installJazzHandsPresenter(ViewPager viewPager) {
        installJazzHandsPresenter(viewPager, false);
    }

    /**
     * Sets a {@link android.support.v4.view.ViewPager.PageTransformer} to the given ViewPager.
     * Using this method to set PageTransformer is required for the ViewPagers that already have
     * {@link JazzHandsAnimationPresenter} installed.
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
        if (tagObject == null || !(tagObject instanceof JazzHandsAnimationPresenter)) {
            viewPager.setPageTransformer(reversDrawingOrder, transformer);
            return;
        }

        final JazzHandsAnimationPresenter presenter = (JazzHandsAnimationPresenter) tagObject;
        ViewPager.PageTransformer jazzHandsTransformerWrapper = new ViewPager.PageTransformer() {
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

        viewPager.setPageTransformer(reversDrawingOrder, jazzHandsTransformerWrapper);
    }

    /**
     * Gets the {@link JazzHandsAnimationPresenter} instance attached to the given ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @return JazzHandsAnimationPresenter instance if set, or null.
     */
    public static JazzHandsAnimationPresenter getJazzHandsAnimationPresenter(ViewPager viewPager) {
        Object tagObject = viewPager.getTag(R.id.presenter_id);
        if (tagObject == null || !(tagObject instanceof JazzHandsAnimationPresenter)) {
            return null;
        }

        return (JazzHandsAnimationPresenter) tagObject;
    }

    /**
     * Convenient method to check whether {@link JazzHandsAnimationPresenter} is attached to the
     * given ViewPager.
     *
     * @param viewPager ViewPager instance.
     * @return True if there is a JazzHandsAnimationPresenter attached, false otherwise.
     */
    public static boolean hasPresenter(ViewPager viewPager) {
        return getJazzHandsAnimationPresenter(viewPager) != null;
    }
}
