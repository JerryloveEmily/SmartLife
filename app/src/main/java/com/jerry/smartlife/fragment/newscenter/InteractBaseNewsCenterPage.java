package com.jerry.smartlife.fragment.newscenter;

import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jerry.smartlife.activity.MainActivity;

/**
 * 新闻中心侧边栏的互动
 * Created by Administrator on 2016/3/2.
 */
public class InteractBaseNewsCenterPage extends BaseNewsCenterPage{


    public InteractBaseNewsCenterPage(MainActivity mainActivity) {
        super(mainActivity);
    }

    @Override
    public View initView() {
        TextView tv = new TextView(mainActivity);
        tv.setText("互动的内容");
        tv.setTextSize(22);
        tv.setTextColor(Color.DKGRAY);
        tv.setGravity(Gravity.CENTER);
        return tv;
    }
}
