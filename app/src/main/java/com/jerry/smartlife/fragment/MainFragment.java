package com.jerry.smartlife.fragment;


import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.smartlife.R;
import com.jerry.smartlife.fragment.base.BaseContentPager;
import com.jerry.smartlife.fragment.base.BaseFragment;

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
    ViewPager mContentPagerView;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private MyOnPagerChangeListener mPagerChangeListener;
    private List<BaseContentPager> mPagers = new ArrayList<>(5);

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
//        View view = View.inflate(mMainActivity, R.layout.fragment_main, null);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void bindView(View view) {
        super.bindView(view);

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
        mContentPagerView.setAdapter(adapter);
    }

    @Override
    public void initEvent() {
        super.initEvent();
        mPagerChangeListener = new MyOnPagerChangeListener();
        mContentPagerView.addOnPageChangeListener(mPagerChangeListener);
    }

    private class MyOnPagerChangeListener implements ViewPager.OnPageChangeListener{

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
            BaseContentPager pager = mPagers.get(position);
            View rootView = pager.getRootView();
            container.addView(rootView);
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
