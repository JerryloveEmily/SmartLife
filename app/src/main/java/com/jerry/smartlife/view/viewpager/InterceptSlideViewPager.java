package com.jerry.smartlife.view.viewpager;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * 拦截滑动的事件的ViewPager
 * Created by Jerry on 2016/3/4.
 */
public class InterceptSlideViewPager extends ViewPager {

    private float donwX = 0, donwY = 0;

    public InterceptSlideViewPager(Context context) {
        super(context);
    }

    public InterceptSlideViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // true 申请父控件不拦截我的touch事件,false 默认父类先拦截事件

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN: // 按下
                getParent().requestDisallowInterceptTouchEvent(true);
                // 获取按下后的坐标
                donwX = ev.getX();
                donwY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE: // 移动
                // 获取移动后的坐标
                float moveX = ev.getX();
                float moveY = ev.getY();

                float dx = moveX - donwX;
                float dy = moveY - donwY;

                // 横向滑动
                if (Math.abs(dx) > Math.abs(dy)){
                    // 如果在第一页或者最后一页，继续滑动的时候
                    if (getCurrentItem() == 0 && dx > 0){
                        // 在第一页，向右滑动的时候，不拦截父容器事件
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else if (getCurrentItem() == getAdapter().getCount() - 1
                            && dx < 0){
                        // 在最后一页，并且继续向左滑动的时候，不拦截父容器事件
                        getParent().requestDisallowInterceptTouchEvent(false);
                    }else {
                        // 其它情况拦截父容器事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }else {
                    // 纵向滑动，不拦截父容器事件
                    getParent().requestDisallowInterceptTouchEvent(false);
                }

                break;
        }

        return super.dispatchTouchEvent(ev);
    }
}
