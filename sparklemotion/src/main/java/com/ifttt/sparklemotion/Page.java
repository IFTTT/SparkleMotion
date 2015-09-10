package com.ifttt.sparklemotion;

/**
 * A component of building an {@link Animation} that contains information about which page(s) the animation should
 * run. For animations that needs to be run for every page, {@link #allPages()} should be used; for animations that
 * only runs for a range of pages, {@link #pageRange(int, int)} should be used; and for animations that only runs
 * for a single page, {@link #singlePage(int)} should be used.
 */
public class Page {

    /**
     * Flag used to indicate that this animation should be run for every page.
     */
    static final int ALL_PAGES = -1;

    /**
     * Index of the starting page of the animation.
     */
    final int start;

    /**
     * Index of the ending page of the animation.
     */
    final int end;

    /**
     * Build a {@link Page} object indicating the animation should be run for all pages.
     *
     * @return Page object.
     */
    public static Page allPages() {
        return new Page(ALL_PAGES, ALL_PAGES);
    }

    /**
     * Build a {@link Page} object indicating the animation should be run for a specific single page.
     *
     * @param page Index of the ViewPager page.
     * @return Page object.
     */
    public static Page singlePage(int page) {
        return new Page(page, page);
    }

    /**
     * Build a {@link Page} object indicating the animation should be run for a specific range of pages.
     *
     * @param start Index of the starting ViewPager page.
     * @param end   Index of the ending ViewPager page.
     *
     * @return Page object.
     *
     * @throws IllegalArgumentException when either {@code start} or {@code end} is smaller than 0 or
     *                                  {@code end} is smaller than {@code start}.
     */
    public static Page pageRange(int start, int end) {
        if (start < 0 || end < 0) {
            throw new IllegalArgumentException("Invalid pages: (" + start + " ," + end + ")");
        }

        if (end < start) {
            throw new IllegalArgumentException("Starting page should be less than or equal to ending page");
        }

        return new Page(start, end);
    }

    private Page(int start, int end) {
        this.start = start;
        this.end = end;
    }

}
