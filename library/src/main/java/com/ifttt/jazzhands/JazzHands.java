package com.ifttt.jazzhands;

import com.ifttt.jazzhands.animations.TranslationAnimation;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Top level Builder class. Used for constructing a {@link JazzHandsAnimationPresenter} and associate
 * it with {@link JazzHandsViewPager}.
 */
public class JazzHands {

    private final JazzHandsViewPager mViewPager;
    private JazzHandsViewPagerLayout mViewPagerLayout;
    private JazzHandsAnimationPresenter mPresenter;

    private boolean mReversedOrder;

    private boolean mDefaultDecorAnimation;

    /**
     * Animations to be used for a set of target Views. Will be cleared after calling {@link #on(int...)}.
     */
    private ArrayList<Animation> mAnimations;

    /**
     * Start constructing a {@link JazzHands} builder with a {@link JazzHandsViewPager} instance. Animations assigned
     * to this builder will be assigned to the ViewPager.
     *
     * @param viewPager Target ViewPager.
     * @return this instance to chain functions.
     */
    public static JazzHands with(JazzHandsViewPager viewPager) {
        return new JazzHands(viewPager);
    }

    /**
     * Start constructing a {@link JazzHands} builder with a {@link JazzHandsViewPagerLayout} instance. Animations
     * assigned to this builder will be assigned to the ViewPager.
     *
     * @param viewPagerLayout TargetViewPagerLayout.
     * @return this instance to chain functions.
     */
    public static JazzHands with(JazzHandsViewPagerLayout viewPagerLayout) {
        return new JazzHands(viewPagerLayout);
    }

    /**
     * Private constructor for initializing the instance with ViewPager {@link JazzHandsViewPager}
     * and {@link JazzHandsAnimationPresenter} reference.
     *
     * @param viewPager JazzHandsViewPager object.
     */
    private JazzHands(JazzHandsViewPager viewPager) {
        mViewPager = viewPager;
        init();
    }

    /**
     * Private constructor for initializing the instance with {@link JazzHandsViewPagerLayout}
     * and {@link JazzHandsAnimationPresenter} reference.
     *
     * @param viewPagerLayout JazzHandsViewPagerLayout object.
     */
    private JazzHands(JazzHandsViewPagerLayout viewPagerLayout) {
        mViewPagerLayout = viewPagerLayout;
        mViewPager = mViewPagerLayout.getViewPager();

        init();
    }

    private void init() {
        if (mViewPager.hasPresenter()) {
            mPresenter = mViewPager.getJazzHandsAnimationPresenter();
        } else {
            mPresenter = new JazzHandsAnimationPresenter();
        }

        mAnimations = new ArrayList<Animation>();
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
     * Use default animations for {@link com.ifttt.jazzhands.JazzHandsViewPagerLayout.Decor} to move along with
     * ViewPager scrolling. If the animation target is not Decor, this attribute will be ignored.
     *
     * @return this instance for chaining.
     */
    public JazzHands defaultDecorAnimation() {
        mDefaultDecorAnimation = true;
        return this;
    }

    /**
     * Assign target {@link com.ifttt.jazzhands.JazzHandsViewPagerLayout.Decor} to JazzHands, which will assign the
     * animations stored in {@link #animate(Animation...)} to {@link JazzHandsAnimationPresenter}. This is the last
     * method to call in order to build a functional JazzHandsViewPager. Once this is called, a {@link
     * JazzHandsAnimationPresenter} will be associated to the ViewPager, and the animations will be run when scrolling.
     * <p/>
     * Note that to use this method, a {@link JazzHandsViewPagerLayout} must be provided.
     *
     * @param decors Target Decors.
     * @throws IllegalStateException when a JazzHandsViewPagerLayout is not provided.
     */
    public void on(JazzHandsViewPagerLayout.Decor... decors) {
        if (mViewPagerLayout == null) {
            throw new IllegalStateException("A JazzHandsViewPagerLayout must be provided");
        }

        mViewPager.setJazzHandsAnimationPresenter(mPresenter, mReversedOrder);

        Animation[] animations = new Animation[mAnimations.size()];
        mAnimations.toArray(animations);

        for (JazzHandsViewPagerLayout.Decor decor : decors) {
            mPresenter.addAnimation(decor, animations);
            mViewPagerLayout.addDecor(decor);
        }

        mAnimations.clear();
    }

    /**
     * Assign target Views to JazzHands, which will assign the animations stored in {@link #animate(Animation...)}
     * to {@link JazzHandsAnimationPresenter}. This is the last method to call in order to build a functional
     * JazzHandsViewPager.
     * Once this is called, a {@link JazzHandsAnimationPresenter} will be associated to the ViewPager, and the
     * animations
     * will be run when scrolling.
     *
     * @param ids Target View ids.
     */
    public void on(int... ids) {
        mViewPager.setJazzHandsAnimationPresenter(mPresenter, mReversedOrder);

        Animation[] anims = new Animation[mAnimations.size()];
        mAnimations.toArray(anims);

        for (int id : ids) {
            mPresenter.addAnimation(id, anims);
        }

        mAnimations.clear();

    }
}
