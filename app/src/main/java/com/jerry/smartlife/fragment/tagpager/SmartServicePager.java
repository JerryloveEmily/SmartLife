package com.jerry.smartlife.fragment.tagpager;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.widget.TextView;

import com.jerry.smartlife.fragment.base.BaseTagPager;

/**
 * 首页
 * Created by JerryloveEmily on 16/2/28.
 */
public class SmartServicePager extends BaseTagPager {

    public SmartServicePager(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();

    }

    @Override
    public void initData() {
        super.initData();
        mTvTitle.setText("智能服务");
        TextView tv = new TextView(mContext);
        tv.setText("智能服务的内容");
        tv.setTextSize(22);
        tv.setTextColor(Color.DKGRAY);
        tv.setGravity(Gravity.CENTER);
        mFlContent.addView(tv);
    }
}
