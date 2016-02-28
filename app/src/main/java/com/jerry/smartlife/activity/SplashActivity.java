package com.jerry.smartlife.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.jerry.smartlife.R;
import com.jerry.smartlife.utils.AppConst;
import com.jerry.smartlife.utils.SharedPUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

public class SplashActivity extends BaseActivity {

    @Bind(R.id.rl_layout)
    RelativeLayout rlLayout;

    private AnimationSet mAnimSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        enterNext();
        initEvent();
    }


    private void initView() {
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
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

    private void startAnimation() {
        mAnimSet = new AnimationSet(true);

        ScaleAnimation sa = new ScaleAnimation(
                0f, 1f,
                0f, 1f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );
        sa.setDuration(2000);
        sa.setFillAfter(true);
        mAnimSet.addAnimation(sa);
        AlphaAnimation aa = new AlphaAnimation(0f, 1f);
        aa.setDuration(2000);
        aa.setFillAfter(true);
        mAnimSet.addAnimation(aa);
        mAnimSet.setDuration(2000);
        mAnimSet.setFillAfter(true);                 //停留在最后的位置
        mAnimSet.setFillEnabled(true);
        rlLayout.startAnimation(mAnimSet);
    }

    private void initEvent() {
    }
}
