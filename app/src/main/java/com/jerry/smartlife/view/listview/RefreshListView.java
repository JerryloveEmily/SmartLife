package com.jerry.smartlife.view.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.jerry.smartlife.R;

/**
 * Created by JerryloveEmily on 16/3/7.
 */
public class RefreshListView extends ListView {

    private LinearLayout mHeaderView;
    private LinearLayout mFooterView;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        initHeader();
        initFooter();
    }

    /**
     * 初始化头部视图
     */
    private void initHeader() {
        mHeaderView = (LinearLayout) View.inflate(getContext(), R.layout.listview_refresh_header, null);
        addHeaderView(mHeaderView);
    }

    /**
     * 初始化底部视图
     */
    private void initFooter() {
        mFooterView = (LinearLayout) View.inflate(getContext(), R.layout.listview_refresh_footer, null);
        addFooterView(mFooterView);
    }

    /**
     * 把轮播图加入headerView中
     * @param view 轮播图视图
     */
    public void addBannerView(View view){
        mHeaderView.addView(view);
    }
}
