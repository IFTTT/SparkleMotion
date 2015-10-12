package com.ifttt.sparklemotion;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

public class CoordinatorAnimBuilder extends ViewAnimationBuilder<View> {

    private final SparkleAnimationBehavior mSparkleAnimationBehavior;

    CoordinatorAnimBuilder(SparkleAnimationBehavior behavior) {
        super(null);
        mSparkleAnimationBehavior = behavior;
    }

    @Override
    public SparkleAnimationPresenter getPresenter(View container) {
        return SparkleMotionCompat.installAnimationPresenter(mSparkleAnimationBehavior);
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
