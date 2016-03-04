package com.jerry.smartlife.activity;

import android.os.Bundle;
import android.os.Handler;

import com.jerry.smartlife.R;
import com.jerry.smartlife.app.AppConst;
import com.jerry.smartlife.utils.SharedPUtil;

public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        enterNext();
    }


    private void initView() {
        setContentView(R.layout.activity_splash);
    }

    private void enterNext(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 判断是否第一次启动app
                if (SharedPUtil.getBoolean(SplashActivity.this, AppConst.ISFIRSTSTART, false)) {
                    // 进入主界面
                    enterActivity(MainActivity.class, true, R.anim.fade_in, R.anim.fade_out);
                } else {
                    // 进入引导页
                    enterActivity(GuideActivity.class, true, R.anim.fade_in, R.anim.fade_out);
                }
            }
        }, 2000);
    }

    @Override
    protected void onDestroy() {
        System.gc();
        super.onDestroy();
    }
}
