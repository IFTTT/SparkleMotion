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
    public boolean layoutDependsOn(CoordinatorLayout parent, T child, View dependency) {
        return super.layoutDependsOn(parent, child, dependency);
    }

    protected abstract float getProgress(T child, View dependency);
}
