package com.ifttt.sparklemotion;

import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * {@link Answer} subclass for mocking setters.
 */
class SetterAnswer implements Answer<Void> {

    float value;

    @Override
    public Void answer(InvocationOnMock invocation) throws Throwable {
        value = (Float) invocation.getArguments()[0];
        return null;
    }
}
