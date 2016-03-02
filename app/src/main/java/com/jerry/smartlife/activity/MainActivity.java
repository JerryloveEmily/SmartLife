package com.jerry.smartlife.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
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
        initDrawerLayout();

        // 初始化菜单栏，内容
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.fl_right_content, new MainFragment(),
                MainFragment.CONTENT_TAG);
        transaction.add(R.id.fl_left_menu, new LeftMenuFragment(),
                LeftMenuFragment.LEFT_MENU_TAG);
        transaction.commit();
    }

    /**
     * 获取左侧菜单栏fragment
     * @return  ...
     */
    public LeftMenuFragment getLeftMenuFragment(){
        return (LeftMenuFragment) getSupportFragmentManager()
                .findFragmentByTag(LeftMenuFragment.LEFT_MENU_TAG);
    }

    /**
     * 获取主内容fragment
     * @return ...
     */
    public MainFragment getMainContentFragment(){
        return (MainFragment) getSupportFragmentManager()
                .findFragmentByTag(MainFragment.CONTENT_TAG);
    }

    private void initDrawerLayout(){
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
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

    @Override
    public void onBackPressed() {
        if(!closeLeftDrawer()){
            super.onBackPressed();
        }
    }

    public DrawerLayout getDrawerLayout(){
        return mDrawerLayout;
    }

    public void toggleDrawerLayout(){
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    public boolean closeLeftDrawer(){
        boolean isClose = false;
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)){
            mDrawerLayout.closeDrawer(Gravity.LEFT);
            isClose = true;
        }
        return isClose;
    }
}
