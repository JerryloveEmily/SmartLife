package com.jerry.smartlife.fragment.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.jerry.smartlife.R;

/**
 * 主界面页面基类
 * Created by JerryloveEmily on 16/2/28.
 */
public class BaseContentPager {

    protected Context mContext;
    protected View mRootView;
    protected ImageButton mIbtnMenu;
    protected TextView mTvTitle;
    protected FrameLayout mFlContent;

    public BaseContentPager(Context context){
        this.mContext = context;
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        mRootView = View.inflate(mContext, R.layout.fragment_content_base_content, null);
        mIbtnMenu = (ImageButton) mRootView.findViewById(R.id.ibtn_menu);
        mTvTitle = (TextView)mRootView.findViewById(R.id.tv_title);
        mFlContent = (FrameLayout) mRootView.findViewById(R.id.fl_content);
    }

    public View getRootView(){
        return mRootView;
    }

    public void initData(){

    }

    public void initEvent(){
        mIbtnMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 打开菜单
                Toast.makeText(mContext, "打开菜单...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
