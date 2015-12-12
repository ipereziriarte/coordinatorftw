package com.letgo.coordinatorftw;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

public class ScrollBtnBehavior extends CoordinatorLayout.Behavior<View> {

    private int toolbarHeight;

    public ScrollBtnBehavior(final Context context, final AttributeSet attrs) {
        super(context, attrs);
        this.toolbarHeight = getToolbarHeight(context);
    }

    @Override
    public boolean layoutDependsOn(final CoordinatorLayout parent, final View child, final View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(final CoordinatorLayout parent, final View child, final View dependency) {
        if (dependency instanceof AppBarLayout) {
            CoordinatorLayout.LayoutParams lp = (CoordinatorLayout.LayoutParams) child.getLayoutParams();
            int viewBottomMargin = lp.bottomMargin;
            int distanceToScroll = child.getHeight() + viewBottomMargin;
            float ratio = (float) dependency.getY() / (float) toolbarHeight;
            child.setTranslationY(-distanceToScroll * ratio);
        }
        return true;
    }

    private int getToolbarHeight(Context context) {
        final TypedArray styledAttributes = context.getTheme().obtainStyledAttributes(
                new int[]{R.attr.actionBarSize});
        int result = (int) styledAttributes.getDimension(0, 0);
        styledAttributes.recycle();


        return result;
    }
}
