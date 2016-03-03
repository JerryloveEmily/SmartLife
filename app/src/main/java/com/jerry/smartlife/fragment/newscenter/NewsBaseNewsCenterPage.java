package com.jerry.smartlife.fragment.newscenter;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.flyco.tablayout.SlidingTabLayout;
import com.jerry.smartlife.R;
import com.jerry.smartlife.activity.MainActivity;
import com.jerry.smartlife.bean.NewsCenterData;
import com.jerry.smartlife.fragment.newscenter.pagetag.PageTagNewsNewsCenterPage;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 新闻中心侧边栏的新闻
 * Created by Administrator on 2016/3/2.
 */
public class NewsBaseNewsCenterPage extends BaseNewsCenterPage {

    @Bind(R.id.newscenter_stl_tab)
    SlidingTabLayout mStlTab;
    @Bind(R.id.newscenter_vp_pager)
    ViewPager mViewPager;

    private MyAdapter myAdapter;

    private List<NewsCenterData.NewsData.ViewTagData> mViewTagDatas;

    public NewsBaseNewsCenterPage(MainActivity mainActivity, List<NewsCenterData.NewsData.ViewTagData> children) {
        super(mainActivity);

        // 获取新闻的页签数据
        this.mViewTagDatas = (children == null)?
                new ArrayList<NewsCenterData.NewsData.ViewTagData>(): new ArrayList<>(children);
    }

    @Override
    public View initView() {
        View view = View.inflate(mainActivity, R.layout.newscenter_news, null);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void initData() {
        super.initData();
        myAdapter = new MyAdapter();
        mViewPager.setAdapter(myAdapter);
        mStlTab.setViewPager(mViewPager);
    }

    @OnClick(R.id.newscenter_ib_nextpage) void nextPageItem(){
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    @Override
    public void initEvent() {
        super.initEvent();

    }

    private class MyAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 初始化每个页签的界面
            PageTagNewsNewsCenterPage page = new PageTagNewsNewsCenterPage(
                    mainActivity, mViewTagDatas.get(position));
            View view = page.getRootView();
            container.addView(view);
            return  view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mViewTagDatas.size();
        }

        /**
         * 页签显示数据调用该方法
         */
        @Override
        public CharSequence getPageTitle(int position) {
            //获取页签的数据
            return mViewTagDatas.get(position).title;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }
}
