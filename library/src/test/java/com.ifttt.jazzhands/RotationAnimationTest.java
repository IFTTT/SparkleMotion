package com.ifttt.jazzhands;

import com.ifttt.jazzhands.animations.RotationAnimation;

import org.junit.Test;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Unit Test for {@link RotationAnimation}.
 */
@SmallTest
public class RotationAnimationTest {

    @Test
    public void testRotationAnimation() throws Exception {
        View rotationView = mock(View.class);
        RotationAnswer answer = new RotationAnswer();
        doAnswer(answer).when(rotationView).setRotation(Mockito.anyFloat());

        RotationAnimation rotationAnimation = new RotationAnimation(0, 270);
        rotationAnimation.onAnimate(rotationView, 0.3f, 0);
        assertEquals(answer.rotation, 270 * 0.3f);
    }

    private static class RotationAnswer implements Answer<Void> {

        float rotation;

        @Override
        public Void answer(InvocationOnMock invocation) throws Throwable {
            rotation = (Float) invocation.getArguments()[0];
            return null;
        }
    }
}
