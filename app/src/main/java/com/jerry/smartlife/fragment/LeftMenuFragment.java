package com.jerry.smartlife.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.jerry.smartlife.R;
import com.jerry.smartlife.bean.NewsCenterData;
import com.jerry.smartlife.fragment.base.BaseFragment;
import com.jerry.smartlife.utils.quickadapter.BaseAdapterHelper;
import com.jerry.smartlife.utils.quickadapter.QuickAdapter;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 左侧菜单
 * Created by JerryloveEmily on 16/2/28.
 */
public class LeftMenuFragment extends BaseFragment {
    public static final String LEFT_MENU_TAG = "LeftMenuFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    @Bind(R.id.lv_left_menu)
    ListView mLvLeftMenu;

    private QuickAdapter<NewsCenterData.NewsData> mAdapter;

    public static LeftMenuFragment newInstance(String param1, String param2) {
        LeftMenuFragment fragment = new LeftMenuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_left_menu, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void initData() {
        mAdapter = new QuickAdapter<NewsCenterData.NewsData>(mMainActivity, R.layout.fragment_left_menu_item) {
            @Override
            protected void convert(BaseAdapterHelper helper, int position, NewsCenterData.NewsData item) {
                TextView tvTitle = helper.getView(R.id.tv_name);
                tvTitle.setText(item.title);
            }
        };
        mLvLeftMenu.setAdapter(mAdapter);
        super.initData();
    }

    public void setLeftMenuData(List<NewsCenterData.NewsData> data){
        mAdapter.addAll(data);
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }
}
