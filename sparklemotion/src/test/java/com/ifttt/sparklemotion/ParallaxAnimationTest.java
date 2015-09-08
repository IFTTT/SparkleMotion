package com.ifttt.sparklemotion;

import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.View;
import com.ifttt.sparklemotion.animations.ParallaxAnimation;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SmallTest
public class ParallaxAnimationTest {

    @Test
    public void testParallaxAnimation() throws Exception {
        View dummyView = mock(View.class);
        when(dummyView.getWidth()).thenReturn(100);

        SetterAnswer answer = new SetterAnswer();
        doAnswer(answer).when(dummyView).setTranslationX(Mockito.anyFloat());
        ParallaxAnimation animation = new ParallaxAnimation(Page.singlePage(0), 4);
        animation.onAnimate(dummyView, 0.3f, 0);

        assertTrue(-7.5f - answer.value < 0.001f);
    }

    @Test
    public void testParallaxAnimationWithDefaultFactor() throws Exception {
        View dummyView = mock(View.class);
        when(dummyView.getWidth()).thenReturn(100);

        SetterAnswer answer = new SetterAnswer();
        doAnswer(answer).when(dummyView).setTranslationX(Mockito.anyFloat());
        ParallaxAnimation animation = new ParallaxAnimation(Page.singlePage(0));
        animation.onAnimate(dummyView, 0.3f, 0);

        assertTrue(-15f - answer.value < 0.001f);

    }
}
