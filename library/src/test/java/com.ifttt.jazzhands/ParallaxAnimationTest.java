package com.ifttt.jazzhands;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import com.ifttt.jazzhands.animations.ParallaxAnimation;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static junit.framework.Assert.assertEquals;
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
        doAnswer(answer).when(dummyView)
                .setTranslationX(Mockito.anyFloat());
        ParallaxAnimation animation = new ParallaxAnimation(0, 4);
        animation.onAnimate(dummyView, 0.3f, 0);

        assertTrue(-7.5f - answer.value < 0.001f);
    }
}
