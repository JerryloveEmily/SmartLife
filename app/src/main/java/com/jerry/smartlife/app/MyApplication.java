package com.jerry.smartlife.app;

import android.app.Application;

import org.xutils.x;

/**
 * Created by Administrator on 2016/3/1.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 初始化xUtils库
        initXUtils();
    }

    private void initXUtils(){
        x.Ext.init(this);
    }
}
