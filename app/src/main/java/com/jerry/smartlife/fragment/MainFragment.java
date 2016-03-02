package com.jerry.smartlife.fragment;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.jerry.smartlife.R;
import com.jerry.smartlife.fragment.base.BaseFragment;
import com.jerry.smartlife.fragment.base.BaseTagPager;
import com.jerry.smartlife.view.LazyLoadViewPager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 主界面
 */
public class MainFragment extends BaseFragment {

    public static final String TAG = "MainFragment";
    public static final String CONTENT_TAG = "MainFragment";


    @Bind(R.id.vp_content_pager)
    LazyLoadViewPager mVpContent;
    @Bind(R.id.tab_bar)
    RadioGroup mRgTabBar;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MyOnPagerChangeListener mPagerChangeListener;
    private List<BaseTagPager> mPagers = new ArrayList<>(5);

    private int mCurrentSelectIndex;    // 当前选择的页面编号

    public MainFragment() {
    }

    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        super.initData();
        // 首页
        mPagers.add(new HomePager(mMainActivity));
        // 新闻中心
        mPagers.add(new NewsCenterPager(mMainActivity));
        // 智能服务
        mPagers.add(new SmartServicePager(mMainActivity));
        // 政务
        mPagers.add(new GovAffairPager(mMainActivity));
        // 设置
        mPagers.add(new SettingCenterPager(mMainActivity));

        MyAdapter adapter = new MyAdapter();
        mVpContent.setAdapter(adapter);

        // 设置默认选中首页
        switchPager();
        // 修改首页tab显示
        mRgTabBar.check(R.id.tab_home);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mPagerChangeListener = new MyOnPagerChangeListener();
        mVpContent.setOnPageChangeListener(mPagerChangeListener);

        mRgTabBar.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.tab_home:
                        mCurrentSelectIndex = 0;
                        break;
                    case R.id.tab_news:
                        mCurrentSelectIndex = 1;
                        break;
                    case R.id.tab_smartservice:
                        mCurrentSelectIndex = 2;
                        break;
                    case R.id.tab_govaffairs:
                        mCurrentSelectIndex = 3;
                        break;
                    case R.id.tab_setting:
                        mCurrentSelectIndex = 4;
                        break;
                }
                switchPager();
            }
        });
    }

    /**
     * 左侧菜单栏选择item子项后转换内容显示页面
     * @param subSelectItemIndex    选择的子菜单项
     */
    public void leftMenuSelectItemSwitchPage(int subSelectItemIndex){
        // 获取用户点击的是底部tab栏的那个页面
        BaseTagPager baseTagPager = mPagers.get(mCurrentSelectIndex);
        // 获取tab栏后，控制用户点击菜单栏子项后显示的界面
        baseTagPager.switchPage(subSelectItemIndex);
    }

    /**
     * 设置选中的页面，切换内容显示页面
     */
    private void switchPager(){
        mVpContent.setCurrentItem(mCurrentSelectIndex, false);

        // 限制第一个和最后一个页面无法画出侧边栏
        if (mCurrentSelectIndex == 0 || mCurrentSelectIndex == mPagers.size() - 1){
            // 不让左侧菜单滑出
            mMainActivity.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        }else {
            // 可以滑出左侧菜单
            mMainActivity.getDrawerLayout().setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
        }
    }

    private class MyOnPagerChangeListener implements LazyLoadViewPager.OnPageChangeListener{

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            Log.e(TAG, "onPageSelected: " + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    private class MyAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            BaseTagPager pager = mPagers.get(position);
            View rootView = pager.getRootView();
            container.addView(rootView);
            Log.e(TAG, "instantiateItem: " + position);
            return rootView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mPagers.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
