package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

final class SparkleAnimationBehavior<T extends View> extends CoordinatorLayout.Behavior<T> {

    private SparkleAnimationPresenter mPresenter;

    private View mDependency;

    private ProgressGenerator<T> mProgressGenerator;

    void installPresenter(@NonNull View dependency, @NonNull SparkleAnimationPresenter presenter,
            @NonNull ProgressGenerator<T> generator) {
        this.mPresenter = presenter;
        this.mDependency = dependency;
        this.mProgressGenerator = generator;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, T child, View dependency) {
        float progress = mProgressGenerator.getProgress(child, dependency);
        mPresenter.presentViewAnimations(progress);
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, T child, View dependency) {
        return dependency == mDependency;
    }
}
