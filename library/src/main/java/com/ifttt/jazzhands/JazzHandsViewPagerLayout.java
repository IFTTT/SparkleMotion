package com.ifttt.jazzhands;

import com.ifttt.jazzhands.animations.TranslationAnimation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.Collections;

/**
 * A wrapper FrameLayout containing a {@link JazzHandsViewPager}. This class supports adding
 * {@link com.ifttt.jazzhands.JazzHandsViewPagerLayout.Decor} to the ViewPager, which can be animated across pages.
 * <p/>
 * A Decor of the ViewPager is only for animation purpose, which means if there's no animation associated with it,
 * it will stay at the original position when it is added to the parent.
 */
public class JazzHandsViewPagerLayout extends FrameLayout implements ViewPager.OnPageChangeListener {

    private JazzHandsViewPager mJazzHandsViewPager;

    /**
     * Index of the ViewPager within this layout.
     */
    private int mViewPagerIndex;

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
        mViewPagerIndex = 0;

        mJazzHandsViewPager.addOnPageChangeListener(this);
    }

    /**
     * Use an external {@link JazzHandsViewPager} instead of the default one as the ViewPager of the layout. The parent
     * of the ViewPager must be null or this layout.
     *
     * @param viewPager ViewPager object to be added to this layout.
     */
    public void setViewPager(@NonNull JazzHandsViewPager viewPager) {
        mJazzHandsViewPager.removeOnPageChangeListener(this);
        removeView(mJazzHandsViewPager);

        mViewPagerIndex = 0;

        if (viewPager.getParent() != null && viewPager.getParent() == this) {
            // ViewPager is already a child View.
            mJazzHandsViewPager.addOnPageChangeListener(this);
            return;
        }

        addView(viewPager, 0);
        mJazzHandsViewPager = viewPager;
        mJazzHandsViewPager.addOnPageChangeListener(this);
    }

    /**
     * Return the {@link JazzHandsViewPager} used in this layout.
     *
     * @return JazzHandsViewPager object.
     */
    public JazzHandsViewPager getViewPager() {
        return mJazzHandsViewPager;
    }

    /**
     * Add {@link com.ifttt.jazzhands.JazzHandsViewPagerLayout.Decor} into this layout. Decor will be added to the
     * layout based on its start page and end page. Calling this method will also enables children drawing order, which
     * follows the order of the parameters, e.g the first Decor will be drawn first.
     *
     * @param decor Decor object to be added to this layout.
     */
    public void addDecor(Decor decor) {
        if (decor == null) {
            return;
        }

        // Make sure there is no duplicate.
        if (mDecors.contains(decor)) {
            return;
        }

        decor.decorIndex = mDecors.size();
        mDecors.add(decor);

        layoutDecors(mJazzHandsViewPager.getCurrentItem());

        setChildrenDrawingOrderEnabled(true);
    }

    /**
     * Remove Decor objects from this layout.
     *
     * @param decor Decor objects to be removed.
     */
    public void removeDecor(Decor decor) {
        int indexOfRemoved = mDecors.indexOf(decor);
        if (indexOfRemoved < 0) {
            throw new IllegalArgumentException("Decor is not added to JazzHandsViewPagerLayout");
        }

        if (decor.isAdded) {
            mDecors.remove(decor);
            removeDecorView(decor);
        }

        int decorSize = mDecors.size();
        for (int i = indexOfRemoved + 1; i < decorSize; i++) {
            mDecors.get(i).decorIndex = i;
        }

        if (mDecors.isEmpty()) {
            // Since there's no Decor left, we can disable children drawing order.
            setChildrenDrawingOrderEnabled(false);
        }
    }

    @Override
    protected int getChildDrawingOrder(int childCount, int i) {
        if (childCount == 1) {
            return super.getChildDrawingOrder(childCount, i);
        }

        if (i == mViewPagerIndex) {
            return 0;
        }

        int index = i > mViewPagerIndex ? i - 1 : i;
        return mDecors.get(index).layoutIndex;
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
     * @param enable Whether or not hardware layer should be used for Decor content views.
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
     * Based on the <code>startPage</code>, <code>endPage</code> and <code>layoutBehindViewPager</code> from
     * {@link com.ifttt.jazzhands.JazzHandsViewPagerLayout.Decor}, show or hide Decors to this FrameLayout.
     *
     * @param currentPageOffset Currently displayed ViewPager page and its offset.
     */
    private void layoutDecors(float currentPageOffset) {
        int decorsSize = mDecors.size();
        for (int i = 0; i < decorsSize; i++) {
            Decor decor = mDecors.get(i);
            if (decor.endPage + 1 <= currentPageOffset && decor.slideOut && decor.slideOutAnimation != null
                    && decor.isAdded) {
                decor.contentView.setVisibility(VISIBLE);
            } else if (decor.startPage != Animation.ALL_PAGES
                    && (decor.startPage > currentPageOffset || decor.endPage < currentPageOffset)
                    && decor.isAdded) {

                // If slide out attribute is true, build a TranslationAnimation for the last page to
                // change the translation X when the ViewPager is scrolling.
                if (decor.slideOutAnimation == null && decor.slideOut) {
                    decor.slideOutAnimation = new TranslationAnimation(
                            decor.endPage, decor.endPage, true, decor.contentView.getTranslationX() - getWidth(),
                            decor.contentView.getTranslationY());
                    mJazzHandsViewPager.getJazzHandsAnimationPresenter().addAnimation(decor, decor.slideOutAnimation);
                } else if (decor.contentView.getVisibility() == VISIBLE
                        && (!decor.slideOut || (decor.endPage + 1 < currentPageOffset))) {
                    decor.contentView.setVisibility(GONE);
                }
            } else if ((decor.startPage <= currentPageOffset
                    && decor.endPage >= currentPageOffset || decor.startPage == Animation.ALL_PAGES)) {
                // If the current page and offset is within the range, add the Decor content View.
                if (!decor.isAdded) {
                    decor.isAdded = true;
                    decor.layoutIndex = getChildCount();
                    Collections.sort(mDecors);
                    addView(decor.contentView);
                    if (decor.layoutBehindViewPage) {
                        mViewPagerIndex++;
                    }
                } else if (decor.contentView.getVisibility() == GONE) {
                    decor.contentView.setVisibility(VISIBLE);
                }
            }
        }
    }

    private void removeDecorView(Decor decor) {
        final int indexOfRemoved = decor.layoutIndex;
        removeView(decor.contentView);

        // Update affected Decors' indices to reflect the change.
        int decorsSize = mDecors.size();
        for (int i = 0; i < decorsSize; i++) {
            if (!mDecors.get(i).isAdded) {
                continue;
            }

            if (mDecors.get(i).layoutIndex > indexOfRemoved) {
                mDecors.get(i).layoutIndex -= 1;
            }
        }

        if (decor.layoutBehindViewPage) {
            mViewPagerIndex--;
        }
    }

    /**
     * An animation decoration of the {@link JazzHandsViewPager}. A Decor will respond to the scrolling of the
     * ViewPager and run animations based on it. One main usage of a Decor is to play cross page animations. Because
     * a Decor is drawn outside of the ViewPager, it won't be clipped by any page, therefore capable of running
     * animations that require Views to go across pages.
     */
    public static class Decor implements Comparable<Decor> {

        /**
         * Content View of this Decor. Must not be null.
         */
        public final View contentView;

        /**
         * The starting page of this Decor. If the current page is smaller than the starting page, the Decor will not
         * be added.
         */
        public final int startPage;

        /**
         * The ending page of thie Decor. If the current page is larger than the ending page, the Decor will not be
         * added.
         */
        public final int endPage;

        /**
         * A flag used to indicate whether this Decor should be drawn behind the ViewPager or not.
         */
        boolean layoutBehindViewPage = false;

        /**
         * A flag indicating whether the Decor has been added to this layout.
         */
        boolean isAdded;

        /**
         * Index of the Decor's content View within the layout.
         */
        int layoutIndex;

        int decorIndex;

        /**
         * Boolean flag to indicate whether this Decor should scroll with ViewPager when it is done.
         */
        boolean slideOut;

        /**
         * Reference to the slide out {@link TranslationAnimation}.
         */
        Animation slideOutAnimation;

        private Decor(
                @NonNull View contentView, int startPage, int endPage, boolean layoutBehind,
                boolean slideOut) {
            this.contentView = contentView;
            this.startPage = startPage;
            this.endPage = endPage;
            this.layoutBehindViewPage = layoutBehind;
            this.slideOut = slideOut;
        }

        @Override
        public int compareTo(@NonNull Decor another) {
            if (isAdded != another.isAdded) {
                return isAdded ? -1 : 1;
            }

            if (layoutBehindViewPage != another.layoutBehindViewPage) {
                return layoutBehindViewPage ? -1 : 1;
            }

            return decorIndex - another.decorIndex;
        }

        /**
         * Builder of the Decor.
         */
        public static class Builder {
            private View mContentView;

            private int mStartPage;
            private int mEndPage;

            private boolean mLayoutBehindViewPage;
            private boolean mSlideOut;

            public Builder() {
                // Set default values for start and end page.
                mStartPage = Animation.ALL_PAGES;
                mEndPage = Integer.MIN_VALUE;
            }

            /**
             * Mandatory View for the content of the Decor.
             *
             * @param contentView View for this Decor, must not be null.
             * @return This object for chaining.
             */
            public Builder setContentView(@NonNull View contentView) {
                mContentView = contentView;
                return this;
            }

            /**
             * Optional starting page of the Decor. The default value is {@link Animation#ALL_PAGES}, which makes the
             * Decor visible in every page.
             *
             * @param startPage Page index that this Decor should start to be visible.
             * @return This object for chaining.
             */
            public Builder setStartPage(int startPage) {
                mStartPage = startPage;
                return this;
            }

            /**
             * Optional ending page of the Decor. The default value is the same as the starting page.
             *
             * @param endPage Page index that this Decor should be removed.
             * @return This object for chaining.
             */
            public Builder setEndPage(int endPage) {
                mEndPage = endPage;
                return this;
            }

            /**
             * Optional attribute for setting the Decor to be drawn behind the ViewPager.
             *
             * @return This object for chaining.
             */
            public Builder behindViewPage() {
                mLayoutBehindViewPage = true;
                return this;
            }

            /**
             * Optional attribute for setting the Decor to scroll along with the page after it passes the end page.
             *
             * @return  This object for chaining.
             */
            public Builder slideOut() {
                mSlideOut = true;
                return this;
            }

            /**
             * Build the Decor based on the attributes set in this Builder.
             *
             * @return Decor object.
             */
            public Decor build() {
                if (mContentView == null) {
                    throw new NullPointerException("Content View cannot be null");
                }

                if (mStartPage >= Animation.ALL_PAGES && mEndPage < Animation.ALL_PAGES) {
                    mEndPage = mStartPage + 1;
                }

                if (mStartPage != Animation.ALL_PAGES &&
                        ((mStartPage < Animation.ALL_PAGES && mEndPage < Animation.ALL_PAGES)
                                || mStartPage > mEndPage)) {
                    throw new IllegalArgumentException(
                            "Invalid startPage or endPage: (" + mStartPage + ", " + mEndPage + ")");
                }

                return new Decor(mContentView, mStartPage, mEndPage, mLayoutBehindViewPage, mSlideOut);
            }
        }

    }
}
