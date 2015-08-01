package com.ifttt.jazzhands;

import org.junit.Test;

import android.test.suitebuilder.annotation.SmallTest;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Unit test for {@link Decor} and its sorting.
 */
@SmallTest
public class DecorSortingTest {

    @Test
    public void sortDecorWithBehind() throws Exception {
        View dummyView = mock(View.class);
        Decor decor0 = new Decor.Builder()
                .setContentView(dummyView)
                .build();

        Decor decor1 = new Decor.Builder()
                .setContentView(dummyView)
                .behindViewPage()
                .build();

        List<Decor> decors = new ArrayList<>();
        decors.add(decor0);
        decors.add(decor1);

        Collections.sort(decors);

        assertEquals(decors.get(0), decor1);
    }

    @Test
    public void sortDecorWithoutBehind() throws Exception {
        View dummyView = mock(View.class);

        Decor decor0 = new Decor.Builder()
                .setContentView(dummyView)
                .build();

        Decor decor1 = new Decor.Builder()
                .setContentView(dummyView)
                .build();

        List<Decor> decors = new ArrayList<>();
        decors.add(decor0);
        decors.add(decor1);

        Collections.sort(decors);

        assertEquals(decors.get(0), decor0);
    }

    @Test
    public void sortDecorWithRemoved() throws Exception {
        View dummyView = mock(View.class);

        Decor decor0 = new Decor.Builder()
                .setContentView(dummyView)
                .build();

        Decor decor1 = new Decor.Builder()
                .setContentView(dummyView)
                .build();

        decor0.isAdded = false;
        decor1.isAdded = true;

        List<Decor> decors = new ArrayList<>();
        decors.add(decor0);
        decors.add(decor1);

        Collections.sort(decors);

        assertEquals(decors.get(0), decor1);
    }

    @Test
    public void testMultipleDecor() throws Exception {
        View dummy = mock(View.class);
        List<Decor> decors = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Decor decor = new Decor.Builder()
                    .setContentView(dummy)
                    .build();
            decor.layoutIndex = 10 - i;
            decor.decorIndex = i;
            decors.add(decor);
        }

        Collections.sort(decors);
        assertEquals(8, decors.get(2).layoutIndex);
        assertEquals(4, decors.get(6).layoutIndex);
    }

    @Test
    public void testMultipleDecorWithBehind() throws Exception {
        View dummy = mock(View.class);
        List<Decor> decors = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Decor decor = new Decor.Builder()
                    .setContentView(dummy)
                    .build();

            if (i == 3) {
                decor.layoutBehindViewPage = true;
            }

            decor.layoutIndex = 10 - i;
            decor.decorIndex = i;
            decors.add(decor);
        }

        Collections.sort(decors);
        assertEquals(3, decors.get(0).decorIndex);
    }

    @Test
    public void testMultipleWithRemoved() throws Exception {
        View dummy = mock(View.class);
        List<Decor> decors = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Decor decor = new Decor.Builder()
                    .setContentView(dummy)
                    .build();

            decor.isAdded = i != 3;

            decor.layoutIndex = 10 - i;
            decor.decorIndex = i;
            decors.add(decor);
        }

        Collections.sort(decors);
        assertEquals(3, decors.get(9).decorIndex);
    }

    @Test
    public void testMultipleWithBehindAndRemoved() throws Exception {
        View dummy = mock(View.class);
        List<Decor> decors = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            Decor decor = new Decor.Builder()
                    .setContentView(dummy)
                    .build();

            decor.isAdded = i != 3;
            if (i % 2 == 0) {
                decor.layoutBehindViewPage = true;
            }

            decor.layoutIndex = 10 - i;
            decor.decorIndex = i;
            decors.add(decor);
        }

        Collections.sort(decors);
        assertEquals(3, decors.get(9).decorIndex);
        assertEquals(0, decors.get(0).decorIndex);
        assertEquals(2, decors.get(1).decorIndex);
        assertEquals(4, decors.get(2).decorIndex);
        assertEquals(6, decors.get(3).decorIndex);
        assertEquals(8, decors.get(4).decorIndex);
    }
}
