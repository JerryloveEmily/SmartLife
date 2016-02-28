package com.jerry.smartlife.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.smartlife.R;
import com.jerry.smartlife.fragment.base.BaseFragment;

/**
 * 左侧菜单
 * Created by JerryloveEmily on 16/2/28.
 */
public class LeftMenuFragment extends BaseFragment {
    public static final String LEFT_MENU_TAG = "LeftMenuFragment";

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        return inflater.inflate(R.layout.fragment_left_menu, container, false);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initEvent() {
        super.initEvent();
    }
}
