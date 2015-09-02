package com.ifttt.sparklemotion;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;
import com.ifttt.sparklemotion.animations.ScaleAnimation;
import org.junit.Test;
import org.mockito.Mockito;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;

/**
 * Unit test for {@link ScaleAnimation}.
 */
@SmallTest
public class ScaleAnimationTest {

    @Test
    public void testScaleAnimation() throws Exception {
        View dummyView = mock(View.class);
        ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.0f, 0f, 0f);

        SetterAnswer answer = new SetterAnswer();
        doAnswer(answer).when(dummyView).setScaleX(Mockito.anyFloat());
        scaleAnimation.onAnimate(dummyView, 0.7f, 0);

        assertTrue(answer.value - 0.3f < 0.001f);
    }
}
