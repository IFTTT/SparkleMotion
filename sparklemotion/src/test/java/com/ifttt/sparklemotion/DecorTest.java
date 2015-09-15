package com.ifttt.sparklemotion;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

@SmallTest
public final class DecorTest {

    @Test
    public void testDecorCreationWithDefaultParams() throws Exception {
        View dummyView = mock(View.class);
        Decor decor = new Decor.Builder(dummyView)
                .build();

        assertEquals(Page.ALL_PAGES, decor.startPage);
        assertEquals(Page.ALL_PAGES, decor.endPage);
        assertEquals(false, decor.layoutBehindViewPage);
        assertEquals(null, decor.slideInAnimation);
        assertEquals(null, decor.slideOutAnimation);
    }

    @Test
    public void testDecorCreationWithPage() throws Exception {
        View dummyView = mock(View.class);
        Decor decorSinglePage = new Decor.Builder(dummyView)
                .setPage(Page.singlePage(0))
                .build();

        assertEquals(0, decorSinglePage.startPage);
        assertEquals(0, decorSinglePage.endPage);

        Decor decorPageRange = new Decor.Builder(dummyView)
                .setPage(Page.pageRange(1, 5))
                .build();

        assertEquals(1, decorPageRange.startPage);
        assertEquals(5, decorPageRange.endPage);
    }

    @Test
    public void testDecorCreationWithSlideIn() throws Exception {
        View dummyView = mock(View.class);
        Decor decorSinglePage = new Decor.Builder(dummyView)
                .setPage(Page.singlePage(1))
                .slideIn()
                .build();

        assertEquals(0, decorSinglePage.startPage);
    }

    @Test
    public void testDecorCreationWithSlideOut() throws Exception {
        View dummyView = mock(View.class);
        Decor decorSinglePage = new Decor.Builder(dummyView)
                .setPage(Page.singlePage(1))
                .slideOut()
                .build();

        assertEquals(2, decorSinglePage.endPage);
    }

    @Test
    public void testDecorCreationWithDifferentOrder() throws Exception {
        View dummyView = mock(View.class);
        Decor decorSinglePage = new Decor.Builder(dummyView)
                .slideOut()
                .slideIn()
                .setPage(Page.singlePage(1))
                .build();

        // The start and end page should not be affected by the order of the method calls.
        assertEquals(0, decorSinglePage.startPage);
        assertEquals(2, decorSinglePage.endPage);
    }
}
