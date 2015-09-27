package com.ifttt.sparklemotion;

import android.support.design.widget.CoordinatorLayout;
import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

public final class CoordinatorAnimBuilder {

    /**
     * Animations to be used for a set of target Views. Will be cleared after calling
     * {@link #on(View)}.
     */
    private final ArrayList<Animation> mAnimations;
    private final SparkleAnimationBehavior mSparkleAnimationBehavior;
    private final SparkleAnimationPresenter mPresenter;

    CoordinatorAnimBuilder(SparkleAnimationBehavior behavior) {
        mAnimations = new ArrayList<>();
        mSparkleAnimationBehavior = behavior;
        mPresenter = SparkleMotionCompat.installAnimationPresenter(behavior);
    }

    public CoordinatorAnimBuilder animate(Animation... animations) {
        Collections.addAll(mAnimations, animations);
        return this;
    }

    public void on(View target) {
        if (!(target.getLayoutParams() instanceof CoordinatorLayout.LayoutParams)) {
            throw new IllegalArgumentException("Container View must be a child View of CoordinatorLayout.");
        }

        Animation[] anims = new Animation[mAnimations.size()];
        mAnimations.toArray(anims);
        mPresenter.addAnimation(target, anims);

        mAnimations.clear();

        CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) target.getLayoutParams();
        lp.setBehavior(mSparkleAnimationBehavior);
    }

}
