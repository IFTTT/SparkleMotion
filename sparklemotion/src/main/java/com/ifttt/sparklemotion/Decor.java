package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.view.View;

/**
 * An animation decoration of the {@link SparkleViewPagerLayout}. A Decor will respond to the
 * scrolling of the ViewPager and run animations based on it. One main usage of a Decor is to play
 * cross page animations. Because a Decor is drawn outside of the ViewPager, it won't be clipped by
 * any page, therefore capable of running animations that require Views to go across pages.
 */
public class Decor implements Comparable<Decor> {

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
    boolean layoutBehindViewPage = false;

    /**
     * Index of the Decor's content View within the layout.
     */
    int layoutIndex;

    int decorIndex;

    /**
     * Boolean flag to indicate whether this Decor should scroll with ViewPager when it is done.
     */
    boolean slideOut;

    private Decor(@NonNull View contentView, @NonNull Page page, boolean layoutBehind,
            boolean slideOut) {
        this.contentView = contentView;
        this.startPage = page.start;
        this.endPage = page.end;
        this.layoutBehindViewPage = layoutBehind;
        this.slideOut = slideOut;
    }

    @Override
    public int compareTo(@NonNull Decor another) {
        if (layoutBehindViewPage != another.layoutBehindViewPage) {
            return layoutBehindViewPage ? -1 : 1;
        }

        return decorIndex - another.decorIndex;
    }

    /**
     * Builder of the Decor.
     */
    public static class Builder {
        @NonNull
        private final View mContentView;

        private Page mPage;

        private boolean mLayoutBehindViewPage;
        private boolean mSlideOut;

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
            return new Decor(mContentView, mPage, mLayoutBehindViewPage, mSlideOut);
        }
    }
}
