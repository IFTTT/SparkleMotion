package com.ifttt.sparklemotion;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import org.junit.Test;

import static junit.framework.Assert.assertEquals;

@SmallTest
public final class AnimationTest {

    @Test
    public void testShouldAnimate() throws Exception {
        Animation animation = new Animation(Page.singlePage(0)) {
            @Override
            public void onAnimate(View v, float offset, float offsetInPixel) {

            }
        };

        assertEquals(true, animation.shouldAnimate(0));
        assertEquals(false, animation.shouldAnimate(-1));
        assertEquals(false, animation.shouldAnimate(2));
    }
}
