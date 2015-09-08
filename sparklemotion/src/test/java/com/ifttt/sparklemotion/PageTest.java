package com.ifttt.sparklemotion;

import android.test.suitebuilder.annotation.SmallTest;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;

@SmallTest
public class PageTest {

    @Test
    public void testSinglePage() throws Exception {
        Page page = Page.singlePage(1);

        assertEquals(page.end, page.start);
        assertEquals(page.start, 1);
    }

    @Test
    public void testPageRange() throws Exception {
        Page page = Page.pageRange(0, 5);

        assertEquals(page.start, 0);
        assertEquals(page.end, 5);
    }

    @Test
    public void testAllPages() throws Exception {
        Page page = Page.allPages();

        assertTrue(page.start < 0);
        assertTrue(page.end < 0);
        assertEquals(page.start, page.end);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeStartPage() throws Exception {
        Page.pageRange(-1, 0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeEndPage() throws Exception {
        Page.pageRange(0, -1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEndLarger() throws Exception {
        Page.pageRange(1, 0);
    }
}
