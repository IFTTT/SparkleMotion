package com.ifttt.jazzhands;

import com.ifttt.jazzhands.animations.Animation;
import com.ifttt.jazzhands.animations.JazzHandsAnimationPresenter;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Top level Builder class. Used for constructing a {@link JazzHandsAnimationPresenter} and associate
 * it with {@link JazzHandsViewPager}.
 */
public class JazzHands {

    private JazzHandsViewPager mViewPager;
    private JazzHandsAnimationPresenter mPresenter;

    private boolean mReversedOrder;

    /**
     * Animations to be used for a set of target Views. Will be cleared after calling {@link #on(int...)}.
     */
    private ArrayList<Animation> mAnimations;

    private int mBroughtToFrontChildIndex = -1;

    /**
     * Private constructor for initializing the instance with ViewPager {@link JazzHandsViewPager}
     * and {@link JazzHandsAnimationPresenter} reference.
     *
     * @param viewPager JazzHandsViewPager object.
     */
    private JazzHands(JazzHandsViewPager viewPager) {
        mViewPager = viewPager;
        if (mViewPager.hasPresenter()) {
            mPresenter = mViewPager.getJazzHandsAnimationPresenter();
        } else {
            mPresenter = new JazzHandsAnimationPresenter();
        }

        mAnimations = new ArrayList<Animation>();
    }

    public static JazzHands with(JazzHandsViewPager viewPager) {
        return new JazzHands(viewPager);
    }

    /**
     * Set child drawing order to reversed for the ViewPager. Default value is false.
     *
     * @return this instance to chain functions.
     */
    public JazzHands reverseDrawingOrder() {
        mReversedOrder = true;
        return this;
    }

    /**
     * Bring the child page at a given index to front by {@link JazzHandsViewPager#bringChildViewToFront(int)}.
     *
     * @param index Page index to be brought to front.
     * @return this instance to chain functions.
     */
    public JazzHands lift(int index) {
        mBroughtToFrontChildIndex = index;
        return this;
    }

    /**
     * Assign animations to JazzHands, which will then associate the animations to target Views.
     *
     * @param animations Animations to run.
     * @return this instance to chain functions.
     */
    public JazzHands animate(Animation... animations) {
        Collections.addAll(mAnimations, animations);
        return this;
    }

    /**
     * Assign target Views to JazzHands, which will assign the animations stored in {@link #animate(Animation...)}
     * to {@link JazzHandsAnimationPresenter}. This is the last method to call in order to build a functional JazzHandsViewPager.
     * Once this is called, a {@link JazzHandsAnimationPresenter} will be associated to the ViewPager, and the animations
     * will be run when scrolling.
     *
     * @param ids Target View ids.
     */
    public void on(int... ids) {
        mViewPager.setJazzHandsAnimationPresenter(mPresenter, mReversedOrder);

        if (mBroughtToFrontChildIndex >= 0) {
            // Bring child to front if it is set.
            mViewPager.bringChildViewToFront(mBroughtToFrontChildIndex);
        }

        Animation[] anims = new Animation[mAnimations.size()];
        mAnimations.toArray(anims);

        for (int id : ids) {
            mPresenter.addAnimation(id, anims);
        }

        mAnimations.clear();

    }

}
