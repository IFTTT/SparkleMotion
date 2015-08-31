package com.ifttt.sparklemotion;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import com.ifttt.sparklemotion.animations.TranslationAnimation;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test for {@link TranslationAnimationTest}.
 */
@SmallTest
public class TranslationAnimationTest {

    private View mDummyView;
    SetterAnswer mXAnswer = new SetterAnswer();
    SetterAnswer mYAnswer = new SetterAnswer();

    @Before
    public void setUp() throws Exception {
        mDummyView = mock(View.class);

        when(mDummyView.getWidth()).thenReturn(100);
        doAnswer(mXAnswer).when(mDummyView).setTranslationX(Mockito.anyFloat());
        doAnswer(mYAnswer).when(mDummyView).setTranslationY(Mockito.anyFloat());
    }

    @Test
    public void testTranslationAnimationWithoutAbsolute() throws Exception {
        TranslationAnimation animation = new TranslationAnimation(0, 0, 200, 200, false);
        animation.onAnimate(mDummyView, 0.3f, 150);

        assertTrue(mXAnswer.value - 60f < 0.001f);
        assertTrue(mYAnswer.value - 60f < 0.001f);

        animation.onAnimate(mDummyView, -0.3f, 150);
        assertTrue(mXAnswer.value - 60f < 0.001f);
        assertTrue(mYAnswer.value - 60f < 0.001f);
    }

    @Test
    public void testTranslationAnimationWithAbsolute() throws Exception {
        TranslationAnimation animation = new TranslationAnimation(0, 0, 200, 200, true);
        animation.onAnimate(mDummyView, 0.3f, -150);

        assertTrue(mXAnswer.value + 90f < 0.001f);
        assertTrue(mYAnswer.value - 60f < 0.001f);

        animation.onAnimate(mDummyView, -0.3f, -150);
        assertTrue(mXAnswer.value + 90f < 0.001f);
        assertTrue(mYAnswer.value - 60f < 0.001f);

        animation.onAnimate(mDummyView, 0.3f, 150);
        assertTrue(mXAnswer.value - 210f < 0.001f);
        assertTrue(mYAnswer.value - 60f < 0.001f);

        animation.onAnimate(mDummyView, -0.3f, 150);
        assertTrue(mXAnswer.value - 210f < 0.001f);
        assertTrue(mYAnswer.value - 60f < 0.001f);
    }
}
