package com.jerry.smartlife.fragment;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.smartlife.fragment.base.BaseTagPager;

/**
 * 首页
 * Created by JerryloveEmily on 16/2/28.
 */
public class HomePager extends BaseTagPager {

    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        mTvTitle.setText("首页");
        // 隐藏打开侧边栏按钮
        mIbtnMenu.setVisibility(View.GONE);
        TextView tv = new TextView(mContext);
        tv.setText("首页的内容");
        tv.setTextSize(22);
        tv.setTextColor(Color.DKGRAY);
        tv.setGravity(Gravity.CENTER);
        mFlContent.addView(tv);
    }
}
