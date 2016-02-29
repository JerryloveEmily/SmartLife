package com.jerry.smartlife.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 不能滑动的ViewPager
 * Created by Jerry on 2016/2/29.
 */
public class NoSlideViewPager extends LazyLoadViewPager {

    private boolean canSlide = false;

    public NoSlideViewPager(Context context) {
        this(context, null);
    }

    public NoSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return canSlide;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return canSlide;
    }

    public boolean canSlide() {
        return canSlide;
    }

    public void setCanSlide(boolean canSlide) {
        this.canSlide = canSlide;
    }
}
