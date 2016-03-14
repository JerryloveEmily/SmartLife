package com.jerry.smartlife.app;

import android.app.Application;
import android.content.Context;

import org.xutils.x;

/**
 * application
 * Created by Jerry on 2016/3/1.
 */
public class MyApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

    }

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
