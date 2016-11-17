package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * An animation decoration of the {@link SparkleViewPagerLayout}. A Decor will respond to the
 * scrolling of the ViewPager and run animations based on it. One main usage of a Decor is to play
 * cross page animations. Because a Decor is drawn outside of the ViewPager, it won't be clipped by
 * any page, therefore capable of running animations that require Views to go across pages.
 */
public class Decor {

    /**
     * Content View of this Decor. Must not be null.
     */
    final View contentView;

    /**
     * The starting page of this Decor. If the current page is smaller than the starting page, the
     * Decor will not be added.
     */
    final int startPage;

    /**
     * The ending page of thie Decor. If the current page is larger than the ending page, the Decor
     * will not be added.
     */
    final int endPage;

    /**
     * A flag used to indicate whether this Decor should be drawn behind the ViewPager or not.
     */
    final boolean layoutBehindViewPage;

    /**
     * {@link Animation} used for sliding the Decor in when the ViewPager is scrolling. This Animation will be applied
     * one page before the Decor's starting page.
     */
    final Animation slideInAnimation;

    /**
     * {@link Animation} used for sliding the Decor out when the ViewPager is scrolling. This Animation will be applied
     * one page after the Decor's ending page.
     */
    final Animation slideOutAnimation;

    /**
     * A flag used to indicate whether the animation(s) running on this Decor should use hardware layer.
     */
    final boolean withLayer;

    Decor(@NonNull View contentView, @NonNull Page page, boolean layoutBehind, Animation slideInAnimation,
          Animation slideOutAnimation, boolean withLayer) {
        this.contentView = contentView;
        this.startPage = page.start;
        this.endPage = page.end;
        this.layoutBehindViewPage = layoutBehind;
        this.slideInAnimation = slideInAnimation;
        this.slideOutAnimation = slideOutAnimation;
        this.withLayer = withLayer;
    }

    /**
     * Builder of the Decor.
     */
    public static class Builder {
        @NonNull
        private final View mContentView;

        private Page mPage;

        private boolean mLayoutBehindViewPage;

        private boolean mSlideIn;

        private boolean mSlideOut;

        private boolean mWithLayer;

        /**
         * @param contentView View for this Decor, must not be null.
         */
        public Builder(@NonNull View contentView) {
            if (contentView == null) {
                throw new NullPointerException("Content View cannot be null");
            }
            mContentView = contentView;
            mPage = Page.allPages();
        }

        /**
         * Optional attribute for setting pages for this Decor. The default value is {@link Page#allPages()}, which
         * means the Decor will exist for all pages in the ViewPager.
         *
         * @param page Page object.
         * @return This object for chaining.
         */
        public Builder setPage(Page page) {
            mPage = page;
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
         * Optional attribute for setting the Decor to be scrolled in when it is shown.
         *
         * @return This object for chaining.
         */
        public Builder slideIn() {
            mSlideIn = true;

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
         * Optional attribute for setting the Decor to animate the View using hardware layer.
         *
         * @return  This object for chaining.
         */
        public Builder withLayer() {
            mWithLayer = true;

            return this;
        }

        /**
         * Build the Decor based on the attributes set in this Builder.
         *
         * @return Decor object.
         */
        public Decor build() {
            Animation slideInAnimation = null;
            Animation slideOutAnimation = null;
            if (mSlideIn && mPage.start > 0) {
                // Slide in animation only applies to a Decor that starts from at least the second page.
                // Will not apply to Decor that is ALL_PAGE.
                mPage = Page.pageRange(Math.max(0, mPage.start - 1), mPage.end);

                Page slideInPage = Page.singlePage(mPage.start);
                slideInAnimation = new SlideInAnimation(slideInPage);
            }

            if (mSlideOut && mPage.end != Page.ALL_PAGES) {
                // Slide out animation will not apply to Decor that is ALL_PAGE.
                mPage = Page.pageRange(mPage.start, mPage.end + 1);

                Page slideOutPage = Page.singlePage(mPage.end);
                slideOutAnimation = new SlideOutAnimation(slideOutPage);
            }
            return new Decor(mContentView, mPage, mLayoutBehindViewPage, slideInAnimation, slideOutAnimation,
                    mWithLayer);
        }
    }
}
