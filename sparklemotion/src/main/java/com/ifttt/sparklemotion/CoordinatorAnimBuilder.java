package com.ifttt.sparklemotion;

import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.view.View;

public class CoordinatorAnimBuilder extends ViewAnimationBuilder<View> {

    private SparkleAnimationBehavior<View> mSparkleAnimationBehavior;

    CoordinatorAnimBuilder(@NonNull View dependency, @NonNull ProgressGenerator<View> progressGenerator) {
        super(dependency);

        mSparkleAnimationBehavior = new SparkleAnimationBehavior<>();
        mSparkleAnimationBehavior.installPresenter(dependency, getPresenter(dependency), progressGenerator);
    }

    @Override
    public SparkleAnimationPresenter getPresenter(View view) {
        return SparkleMotionCompat.installAnimationPresenter(view);
    }

    @Override
    public void on(Object target) {
        super.on(target);

        if (target instanceof View && ((View) target).getParent() instanceof CoordinatorLayout) {
            View view = (View) target;
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) view.getLayoutParams();
            lp.setBehavior(mSparkleAnimationBehavior);
        }
    }
}
