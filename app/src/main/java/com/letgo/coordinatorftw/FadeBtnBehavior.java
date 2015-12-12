package com.letgo.coordinatorftw;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPropertyAnimatorListener;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Interpolator;

/**
 * Defines the behavior of the button relative to the parent layout
 */
@SuppressWarnings("unused")
public class FadeBtnBehavior extends CoordinatorLayout.Behavior<View> {

    private static final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();
    private boolean isAnimatingOut = false;

    /**
     * This constructor must be created in order to avoid inflate exceptions
     *
     * @param context a context for inflation
     * @param attrs the attributes for the layout theme
     */
    @SuppressWarnings("unused")
    public FadeBtnBehavior(Context context, AttributeSet attrs) {
        super();
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final View child, final View dependency) {
        return dependency instanceof RecyclerView;
    }

    @Override
    public boolean onStartNestedScroll(final CoordinatorLayout coordinatorLayout, final View child,
            final View directTargetChild,
            final View target, final int nestedScrollAxes) {
        return nestedScrollAxes == ViewCompat.SCROLL_AXIS_VERTICAL ||
                super.onStartNestedScroll(coordinatorLayout, child, directTargetChild, target, nestedScrollAxes);
    }

    @Override
    public void onNestedScroll(final CoordinatorLayout coordinatorLayout, final View child, final View target,
            final int dxConsumed,
            final int dyConsumed, final int dxUnconsumed, final int dyUnconsumed) {
        super.onNestedScroll(coordinatorLayout, child, target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed);
        if (dyConsumed > 0 && !this.isAnimatingOut && child.getVisibility() == View.VISIBLE) {
            animateOut(child);
        } else if (dyConsumed < 0 && child.getVisibility() != View.VISIBLE) {
            animateIn(child);
        }
    }

    /**
     * Animate the button to make it disappear. It's the same one used by FAB button
     *
     * @param button to animate
     */
    private void animateOut(final View button) {
        ViewCompat.animate(button).scaleX(0.0f).scaleY(0.0f).setInterpolator(INTERPOLATOR).withLayer()
                .setListener(new ViewPropertyAnimatorListener() {
                    @Override
                    public void onAnimationStart(final View view) {
                        FadeBtnBehavior.this.isAnimatingOut = true;
                    }

                    @Override
                    public void onAnimationEnd(final View view) {
                        FadeBtnBehavior.this.isAnimatingOut = false;
                        view.setVisibility(View.GONE);
                    }

                    @Override
                    public void onAnimationCancel(final View view) {
                        FadeBtnBehavior.this.isAnimatingOut = false;
                    }
                }).start();
    }

    /**
     * Animate the button to be visible in the view. It's the same one used in the FAB button
     *
     * @param button to animate
     */
    private void animateIn(final View button) {
        button.setVisibility(View.VISIBLE);
        ViewCompat.animate(button).scaleX(1.0f).scaleY(1.0f).alpha(1.0f)
                .setInterpolator(INTERPOLATOR).withLayer().setListener(null)
                .start();
    }

}
