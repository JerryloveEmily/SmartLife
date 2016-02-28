package com.jerry.smartlife.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SlidingPaneLayout;
import android.view.View;
import android.widget.FrameLayout;

import com.jerry.smartlife.R;
import com.jerry.smartlife.fragment.LeftMenuFragment;
import com.jerry.smartlife.fragment.MainFragment;
import com.jerry.smartlife.view.PagerEnabledSlidingPaneLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.spl_layout)
    PagerEnabledSlidingPaneLayout mSlidingLayout;
    @Bind(R.id.fl_left_menu)
    FrameLayout mFlMenuLayout;
    @Bind(R.id.fl_right_content)
    FrameLayout mFlContentLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initEvent();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // 初始化菜单栏，内容
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_right_content, MainFragment.newInstance("", ""),
                MainFragment.CONTENT_TAG);
        transaction.add(R.id.fl_left_menu, LeftMenuFragment.newInstance("", ""),
                LeftMenuFragment.LEFT_MENU_TAG);
        transaction.commit();
    }

    private void initEvent(){
        mSlidingLayout.setPanelSlideListener(new SlidingPaneLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelOpened(View panel) {

            }

            @Override
            public void onPanelClosed(View panel) {

            }
        });

    }

}
