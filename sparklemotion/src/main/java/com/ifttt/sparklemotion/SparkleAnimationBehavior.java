package com.ifttt.sparklemotion;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

public abstract class SparkleAnimationBehavior<T extends View> extends CoordinatorLayout.Behavior<T> {

    private SparkleAnimationPresenter mPresenter;

    void installPresenter(SparkleAnimationPresenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, T child, View dependency) {
        float progress = getProgress(child, dependency);
        mPresenter.presentViewAnimations(progress);
        return true;
    }

    @Override
    public abstract boolean layoutDependsOn(CoordinatorLayout parent, T child, View dependency);

    SparkleAnimationPresenter getPresenter() {
        return mPresenter;
    }

    protected abstract float getProgress(T child, View dependency);
}
