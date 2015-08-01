package com.ifttt.jazzhands;

import com.ifttt.jazzhands.animations.AlphaAnimation;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Unit test for {@link AlphaAnimation}.
 */
@SmallTest
public class AlphaAnimationTest {

    @Test
    public void testAlphaValue() throws Exception {
        View alphaView = mock(View.class);
        SetterAnswer answer = new SetterAnswer();
        doAnswer(answer).when(alphaView).setAlpha(Mockito.anyFloat());

        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 0.8f, 0f);
        alphaAnimation.onAnimate(alphaView, 0.3f, 0);
        assertTrue(answer.value - 0.24f < 0.001f);
    }
}
