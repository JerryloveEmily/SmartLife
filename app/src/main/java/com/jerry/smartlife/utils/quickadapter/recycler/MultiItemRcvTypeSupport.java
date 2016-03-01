package com.jerry.smartlife.utils.quickadapter.recycler;

public interface MultiItemRcvTypeSupport<T> {

    int getLayoutId(int viewType);

    int getItemViewType(int position, T t);

}