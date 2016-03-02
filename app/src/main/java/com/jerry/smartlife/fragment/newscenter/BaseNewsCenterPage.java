package com.jerry.smartlife.fragment.newscenter;

import android.view.View;

import com.jerry.smartlife.activity.MainActivity;

/**
 * 新闻中心内容基类
 * Created by Administrator on 2016/3/2.
 */
public abstract class BaseNewsCenterPage {

    protected MainActivity mainActivity;
    protected View mRootView;

    BaseNewsCenterPage(MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        mRootView = initView();
        initEvent();
    }

    public View getRootView() {
        return mRootView;
    }

    /**
     * 初始化视图
     * @return 返回根视图
     */
    public abstract View initView();

    /**
     * 初始化数据
     */
    public void initData(){

    }

    /**
     * 初始化事件
     */
    public void initEvent() {

    }
}
