package com.jerry.refreshviewlibrary.listview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jerry.refreshviewlibrary.R;
import com.jerry.refreshviewlibrary.test.JTestLog;

import java.text.SimpleDateFormat;
import java.util.Date;

/**可下拉刷新和加载更多的ListView
 * Created by JerryloveEmily on 16/3/7.
 */
public class RefreshListView extends ListView {

    private LinearLayout mHeaderView;                    // 头部视图
    private TextView mHeaderStateText;               // 头部状态更新文本视图
    private TextView mHeaderDateText;                // 头部刷新时间文本视图
    private ImageView mHeaderArrowView;               // 头部下拉箭头视图
    private ProgressBar mHeaderRefreshView;             // 头部正在刷新进度条视图
    private LinearLayout mFooterView;                    // 尾部视图
    private LinearLayout mLLRefreshHeadLayout;           // 头部刷新视图
    private View mBannerView;                    // 轮播图视图

    private int mHeadLayoutHeight;              // 刷新头布局的高度
    private int mFootLayoutHeight;              // 加载更多尾部布局的高度

    private float mDownY = -1;
    private int listviewOnScreenY;              // listview在屏幕中的Y值
    private final int PULL_DOWN = 1;            // 下拉刷新状态
    private final int RELEASE_STATE = 2;            // 松开刷新
    private final int REFRESHING = 3;            // 正在刷新
    private int currentState = PULL_DOWN;    // 当前的状态
    private RotateAnimation mArrowUpAnim;
    private RotateAnimation mArrowDownAnim;
    private boolean isPullRefreshHeaderEnable = false;
    private boolean isLoadMoreFooterEnable = false;
    private boolean isLoadMore;

    public RefreshListView(Context context) {
        this(context, null);
    }

    public RefreshListView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
        initArrowAnimation();
        initEvent();
    }

    private void initView() {
        initHeader();
        initFooter();
    }

    private void initEvent() {
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (!isLoadMoreFooterEnable) {
                    //不启用加载更多数据的功能
                    return ;
                }

                // 当listview滚动停止，并且显示出最后一条的时候，加载更多数据
                if (getLastVisiblePosition() == getAdapter().getCount() - 1 && !isLoadMore) {
                    // 显示最后一条item的时候，显示出加载更多数据的视图
                    mFooterView.setPadding(0, 0, 0, 0);
                    setSelection(getAdapter().getCount());

                    // 加载更多数据
                    isLoadMore = true;
                    if (onRefreshAndLoadMoreListener != null) {
                        // 实现该接口完成加载更多数据功能
                        onRefreshAndLoadMoreListener.onLoadingMore();
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                if (!isPullRefreshHeaderEnable) {
                    // 没有启用下拉刷新功能
                    return super.onTouchEvent(ev);
                }

                // 是否处于正在刷新的状态
                if (currentState == REFRESHING) {
                    return super.onTouchEvent(ev);
                }

                if (!isBannerViewFullShow()) {
                    // 轮播图没有完全显示,使用listview的touch事件来处理return super.onTouchEvent(ev);
                    return super.onTouchEvent(ev);
                }

                if (mDownY == -1) { // 防止按下的时候没有获取按下的Y坐标值
                    mDownY = ev.getY();
                }

                float moveY = ev.getY();
                float dy = moveY - mDownY;

                // 当向下拉动，并且listview的第一个item可见的时候表示是下拉刷新
                if (dy > 0 && getFirstVisiblePosition() == 0) {
                    // 滚动后paddingTop内缩的距离，当前paddingTop的值
                    float scrolledYDis = -mHeadLayoutHeight + dy;

                    if (scrolledYDis < 0 && currentState != PULL_DOWN) {
                        currentState = PULL_DOWN;// 让这只执行一次
                        refreshState();

                    } else if (scrolledYDis > 0 && currentState != RELEASE_STATE) {
                        currentState = RELEASE_STATE;
                        refreshState();

                    }

                    mLLRefreshHeadLayout.setPadding(0, (int) scrolledYDis, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                mDownY = -1;
                if (currentState == PULL_DOWN) {
                    mLLRefreshHeadLayout.setPadding(0, -mHeadLayoutHeight, 0, 0);
                } else if (currentState == RELEASE_STATE) {
                    // 刷新数据
                    mLLRefreshHeadLayout.setPadding(0, 0, 0, 0);
                    // 改变状态为正在刷新状态
                    currentState = REFRESHING;
                    refreshState();
                    if (null != onRefreshAndLoadMoreListener) {
                        // 开始刷新数据
                        onRefreshAndLoadMoreListener.onRefreshing();
                    }
                }
                break;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 更新状态
     */
    private void refreshState() {
        switch (currentState) {
            case PULL_DOWN:// 下拉刷新
                JTestLog.e("下拉刷新...");
                mHeaderStateText.setText("下拉刷新");
                mHeaderArrowView.setVisibility(VISIBLE);
                mHeaderRefreshView.setVisibility(GONE);
                mHeaderArrowView.startAnimation(mArrowDownAnim);
                break;
            case RELEASE_STATE:// 松开刷新
                JTestLog.e("松开刷新...");
                mHeaderStateText.setText("松开刷新");
                mHeaderArrowView.setVisibility(VISIBLE);
                mHeaderRefreshView.setVisibility(GONE);
                mHeaderArrowView.startAnimation(mArrowUpAnim);
                break;
            case REFRESHING:// 正在刷新
                mHeaderArrowView.clearAnimation();
                mHeaderStateText.setText("正在刷新...");
                mHeaderArrowView.setVisibility(GONE);
                mHeaderRefreshView.setVisibility(VISIBLE);
                break;
        }
    }

    public void refreshFinish() {
        // 加载更多数据
        if (isLoadMore){
            isLoadMore = false;
            // 向上隐藏尾部布局
            mFooterView.setPadding(0, -mFootLayoutHeight, 0, 0);
        }else {

            // 下拉刷新
            mHeaderStateText.setText("下拉刷新");
            mHeaderArrowView.setVisibility(VISIBLE);
            mHeaderRefreshView.setVisibility(GONE);
            mHeaderDateText.setText(getCurrentFormatData());
            mLLRefreshHeadLayout.setPadding(0, -mHeadLayoutHeight, 0, 0);
            currentState = PULL_DOWN;
        }
    }

    private String getCurrentFormatData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dateFormat.format(new Date());
    }

    private void initArrowAnimation() {
        mArrowUpAnim = new RotateAnimation(0, -180,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mArrowUpAnim.setDuration(500);
        mArrowUpAnim.setFillAfter(true);

        mArrowDownAnim = new RotateAnimation(-180, 0,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);
        mArrowDownAnim.setDuration(500);
        mArrowDownAnim.setFillAfter(true);
    }

    /**
     * 判断轮播图是否完全显示
     *
     * @return true 完全显示
     */
    private boolean isBannerViewFullShow() {
        if(mBannerView == null){
            // 当做轮播图已经完全显示了
            return true;
        }
        // 判断轮播图是否完整显示，完整显示了才显示下拉刷新
        // 获取listview屏幕中的Y值
        int[] location = new int[2];
        if (listviewOnScreenY == 0) {
            getLocationOnScreen(location);
            listviewOnScreenY = location[1];
        }
        mBannerView.getLocationOnScreen(location);
        // 没有完全显示轮播图视图
        return location[1] >= listviewOnScreenY;
    }

    /**
     * 初始化头部视图
     */
    private void initHeader() {
        mHeaderView = (LinearLayout) View.inflate(getContext(), R.layout.listview_header_container, null);
        mLLRefreshHeadLayout = (LinearLayout) mHeaderView.findViewById(R.id.ll_refresh_container);

        // 头部刷新视图组件初始化
        mHeaderStateText = (TextView) mHeaderView.findViewById(R.id.tv_listview_head_refresh_status);
        mHeaderDateText = (TextView) mHeaderView.findViewById(R.id.tv_listview_head_refresh_date);
        mHeaderArrowView = (ImageView) mHeaderView.findViewById(R.id.iv_listview_head_arrow);
        mHeaderRefreshView = (ProgressBar) mHeaderView.findViewById(R.id.pb_listview_head);

        // 测量刷新头布局的高度
        mLLRefreshHeadLayout.measure(0, 0);
        // 获取刷新头的布局测量的高度
        mHeadLayoutHeight = mLLRefreshHeadLayout.getMeasuredHeight();
        // 向上隐藏刷新头的布局
        mLLRefreshHeadLayout.setPadding(0, -mHeadLayoutHeight, 0, 0);
        addHeaderView(mHeaderView);
    }

    /**
     * 初始化底部视图
     */
    private void initFooter() {
        mFooterView = (LinearLayout) View.inflate(getContext(), R.layout.listview_refresh_footer, null);

        // 测量尾部布局的高度
        mFooterView.measure(0, 0);
        // 获取尾部布局测量的高度
        mFootLayoutHeight = mFooterView.getMeasuredHeight();
        // 向上隐藏尾部布局
        mFooterView.setPadding(0, -mFootLayoutHeight, 0, 0);
        addFooterView(mFooterView);
    }

    /**
     * 设置是否启用下拉刷新功能
     *
     * @param isPullRefreshHeaderEnable ...
     */
    public void setIsPullRefreshHeaderEnable(boolean isPullRefreshHeaderEnable) {
        this.isPullRefreshHeaderEnable = isPullRefreshHeaderEnable;
    }

    /**
     * 设置是否启用加载更多数据功能
     * @param isLoadMoreFooterEnable ...
     */
    public void setIsLoadMoreFooterEnable(boolean isLoadMoreFooterEnable){
        this.isLoadMoreFooterEnable = isLoadMoreFooterEnable;
    }

    /**
     * 把轮播图加入headerView中
     *
     * @param view 轮播图视图
     */
    @Override
    public void addHeaderView(View view) {
        // 判断是否设置允许使用下拉刷新头布局，否则使用listview的headerview
        if (isPullRefreshHeaderEnable) {
            // 启用下拉刷新
            this.mBannerView = view;
            mHeaderView.addView(view);
        } else {
            super.addHeaderView(view);
        }
    }

    private OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener;

    public void setOnRefreshAndLoadMoreListener(OnRefreshAndLoadMoreListener onRefreshAndLoadMoreListener) {
        this.onRefreshAndLoadMoreListener = onRefreshAndLoadMoreListener;
    }

    public interface OnRefreshAndLoadMoreListener {
        void onRefreshing();

        void onLoadingMore();
    }
}
