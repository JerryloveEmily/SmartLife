package com.jerry.smartlife.fragment.newscenter.pagetag;

import android.view.View;

import com.jerry.smartlife.R;
import com.jerry.smartlife.activity.MainActivity;
import com.jerry.smartlife.bean.NewsCenterData;

/**
 * 新闻中心新闻的页签的页面
 *
 * Created by JerryloveEmily on 16/3/3.
 */
public class PageTagNewsNewsCenterPage {

    private MainActivity mainActivity;
    private View mRootView;

    private NewsCenterData.NewsData.ViewTagData mViewTagData;

    public PageTagNewsNewsCenterPage(MainActivity mainActivity, NewsCenterData.NewsData.ViewTagData viewTagData){
        this.mainActivity = mainActivity;
        this.mViewTagData = viewTagData;
        initView();
        initData();
        initEvent();
    }

    private void initView(){
        mRootView = View.inflate(mainActivity, R.layout.newscenter_page_tag_news_content, null);
    }

    private void initData(){

    }

    private void initEvent(){

    }

    public View getRootView() {
        return mRootView;
    }
}
