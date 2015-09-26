package com.ifttt.sparklemotion;

public interface AnimBuilder<T> {

    AnimBuilder<T> animate(Animation... animations);

    void on(T target);
}
