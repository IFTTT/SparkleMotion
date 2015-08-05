package com.ifttt.jazzhands;

import android.support.annotation.NonNull;
import android.view.View;
import com.ifttt.jazzhands.animations.TranslationAnimation;

/**
 * An animation decoration of the {@link JazzHandsViewPagerLayout}. A Decor will respond to the
 * scrolling of the ViewPager and run animations based on it. One main usage of a Decor is to play
 * cross page animations. Because a Decor is drawn outside of the ViewPager, it won't be clipped by
 * any page, therefore capable of running animations that require Views to go across pages.
 */
public class Decor implements Comparable<Decor> {

    /**
     * Content View of this Decor. Must not be null.
     */
    public final View contentView;

    /**
     * The starting page of this Decor. If the current page is smaller than the starting page, the
     * Decor will not be added.
     */
    public final int startPage;

    /**
     * The ending page of thie Decor. If the current page is larger than the ending page, the Decor
     * will not be added.
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

    private Decor(@NonNull View contentView, int startPage, int endPage, boolean layoutBehind,
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
         * Optional starting page of the Decor. The default value is {@link Animation#ALL_PAGES},
         * which makes the Decor visible in every page.
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
         * Optional attribute for setting the Decor to scroll along with the page after it passes
         * the end page.
         *
         * @return This object for chaining.
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

            if (mStartPage != Animation.ALL_PAGES && (
                    (mStartPage < Animation.ALL_PAGES && mEndPage < Animation.ALL_PAGES)
                            || mStartPage > mEndPage)) {
                throw new IllegalArgumentException(
                        "Invalid startPage or endPage: (" + mStartPage + ", " + mEndPage + ")");
            }

            return new Decor(mContentView, mStartPage, mEndPage, mLayoutBehindViewPage, mSlideOut);
        }
    }
}
