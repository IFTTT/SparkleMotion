package com.ifttt.sparklemotion;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import com.ifttt.sparklemotion.animations.RotationAnimation;
import org.junit.Test;
import org.mockito.Mockito;

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
        SetterAnswer answer = new SetterAnswer();
        doAnswer(answer).when(rotationView).setRotation(Mockito.anyFloat());

        RotationAnimation rotationAnimation = new RotationAnimation(0, 270);
        rotationAnimation.onAnimate(rotationView, 0.3f, 0);
        assertEquals(answer.value, 270 * 0.3f);
    }
}
