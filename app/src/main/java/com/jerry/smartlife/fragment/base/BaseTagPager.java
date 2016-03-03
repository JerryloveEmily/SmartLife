package com.jerry.smartlife.fragment.base;

import android.content.Context;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.jerry.smartlife.R;
import com.jerry.smartlife.activity.MainActivity;

/**
 * 主界面页面基类
 * Created by JerryloveEmily on 16/2/28.
 */
public class BaseTagPager {

    protected Context mContext;
    protected View mRootView;
    protected ImageButton mIbtnMenu;
    protected TextView mTvTitle;
    protected FrameLayout mFlContent;

    public BaseTagPager(Context context){
        this.mContext = context;
        initView();
//        initData();
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
                // 打开或者关闭左侧菜单
                ((MainActivity) mContext).toggleDrawerLayout();
            }
        });
    }

    /**
     * 有好几个Viewpager主页tab页面是有侧边栏菜单可以用来切换内容显示视图
     * @param position  侧边栏菜单点击的子项
     */
    public void switchPage(int position){
    }
}
