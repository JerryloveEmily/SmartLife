package com.jerry.smartlife.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;
import android.widget.FrameLayout;

import com.jerry.smartlife.R;
import com.jerry.smartlife.fragment.LeftMenuFragment;
import com.jerry.smartlife.fragment.MainFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @Bind(R.id.dl_layout)
    DrawerLayout mDrawerLayout;
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

        initDrawerLayout();
    }

    public LeftMenuFragment getLeftMenuFragment(){
        return (LeftMenuFragment) getSupportFragmentManager().findFragmentByTag(LeftMenuFragment.LEFT_MENU_TAG);
    }

    private void initDrawerLayout(){
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, null,
                R.string.app_name, R.string.app_name){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerToggle.syncState();
        mDrawerLayout.setDrawerListener(drawerToggle);
    }

    private void initEvent(){

    }

    public DrawerLayout getDrawerLayout(){
        return mDrawerLayout;
    }
}
