package com.jerry.smartlife.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jerry.smartlife.activity.MainActivity;

/**
 * Created by JerryloveEmily on 16/2/28.
 */
public abstract class BaseFragment extends Fragment {

    protected MainActivity mMainActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mMainActivity = (MainActivity) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return initView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        bindView(view);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * 子类初始化布局
     * @param inflater           ...
     * @param container          ...
     * @param savedInstanceState ...
     * @return                   布局视图对象
     */
    public abstract View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public void bindView(View view){

    }

    /**
     * 初始化数据
     */
    public void initData(){

    }

    /**
     * 初始化事件
     */
    public void initEvent(){

    }
}
