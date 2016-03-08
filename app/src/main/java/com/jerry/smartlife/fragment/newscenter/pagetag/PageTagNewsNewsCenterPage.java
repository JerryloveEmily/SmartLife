package com.jerry.smartlife.fragment.newscenter.pagetag;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.smartlife.R;
import com.jerry.smartlife.activity.MainActivity;
import com.jerry.smartlife.app.AppConst;
import com.jerry.smartlife.bean.NewsCenterData;
import com.jerry.smartlife.bean.PageTagNewsData;
import com.jerry.smartlife.utils.DensityUtil;
import com.jerry.smartlife.utils.SharedPUtil;
import com.jerry.smartlife.utils.quickadapter.BaseAdapterHelper;
import com.jerry.smartlife.utils.quickadapter.QuickAdapter;
import com.jerry.smartlife.view.listview.RefreshListView;
import com.jerry.smartlife.view.viewpager.scroller.ViewPagerScroller;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * 新闻中心新闻的页签的页面
 * <p/>
 * Created by JerryloveEmily on 16/3/3.
 */
public class PageTagNewsNewsCenterPage {

    // 每隔5秒自动滚动轮播图
    private static final int BANNER_AUTO_SCROLL_DELAY_TIME = 5000;

    private MainActivity mainActivity;
    private View mRootView;
    private View mBannerView;
    //    @Bind(R.id.vp_banner)
    private ViewPager mVpBanner;        // 轮播图广告条
    //    @Bind(R.id.tv_title)
    TextView mTvBannerTitle;    // 轮播图描述
    //    @Bind(R.id.ll_points)
    private LinearLayout mLLPoints;     // 轮播图点的指示容器

    @Bind(R.id.lv_content)
    RefreshListView mLvContent;        // 新闻内容列表

    private ViewPagerScroller mBannerScroller;
    private BannerAdapter mBannerAdapter;       // 轮播图适配器
    private NewsCenterData.NewsData.ViewTagData mViewTagData;
    private Gson mGson;
    private Handler mHandler;
    // 新闻列表适配器
    private QuickAdapter<PageTagNewsData.NewsContent.ListNewsData> mNewsAdapter;
    private List<PageTagNewsData.NewsContent.TopNewsData> mTopNewsDatas = new ArrayList<>();

    private int mCurrentBannerIndex;    // 当前轮播图的位置
    private boolean isRefresh = false;          // 表示是下拉刷新数据
    private String mLoadMoreDataUrl;

    public PageTagNewsNewsCenterPage(MainActivity mainActivity, NewsCenterData.NewsData.ViewTagData viewTagData) {
        this.mainActivity = mainActivity;
        this.mViewTagData = viewTagData;
        mGson = new Gson();
        initView();
        initData();
        initEvent();
    }

    private void initView() {
        // 页签页面的根布局
        mRootView = View.inflate(mainActivity, R.layout.newscenter_page_tag_news_content, null);
        ButterKnife.bind(this, mRootView);

        /*在listview的header加入视图的两个坑：*/
        /**
         *1. listView的HeaderView不能通过以下方式加载的view来添加
         *   mBannerView = View.inflate(mainActivity, R.layout.newscenter_page_tag_news_banner, null);
         *
         *2. 如下动态加载一个布局添加到listview的这种情况的，无法使用ButterKnife来做视图注解
         *   ButterKnife.bind(this, mBannerView);
         */
        mBannerView = LayoutInflater.from(mainActivity).inflate(
                R.layout.newscenter_page_tag_news_banner,
                mLvContent, false);
        mVpBanner = (ViewPager) mBannerView.findViewById(R.id.vp_banner);
        mTvBannerTitle = (TextView) mBannerView.findViewById(R.id.tv_title);
        mLLPoints = (LinearLayout) mBannerView.findViewById(R.id.ll_points);
        // 把轮播图添加到listview中
        mLvContent.setIsPullRefreshHeaderEnable(true);
        mLvContent.addHeaderView(mBannerView);
    }

    private void initData() {
        // 设置轮播图的适配器
        mBannerAdapter = new BannerAdapter();
        mVpBanner.setAdapter(mBannerAdapter);
        // 修改viewpager滚动一页的时间,默认是1秒
        mBannerScroller = new ViewPagerScroller(mainActivity);
        mBannerScroller.initViewPagerScroller(mVpBanner);
        initNewsListView();

        // 获取本地缓存数据
        String jsonCache = SharedPUtil.getString(mainActivity, AppConst.NEWSNEWSCENTERCACHE, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            // 有缓存数据，处理数据
            PageTagNewsData pageTagNewsData = parseJsonData(jsonCache);
            processData(pageTagNewsData);
        }
        // 从网络获取数据
        getDataFromNet(false);
    }

    private void initEvent() {
        mVpBanner.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            /**
             * 停留到的页面
             * @param position ...
             */
            @Override
            public void onPageSelected(int position) {
                mCurrentBannerIndex = position;
                setBannerImageTitleAndSelectPoint(mCurrentBannerIndex);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        mLvContent.setOnRefreshAndLoadMoreListener(new RefreshListView.OnRefreshAndLoadMoreListener() {
            @Override
            public void onRefreshing() {
                isRefresh = true;
                getDataFromNet(false);
            }

            @Override
            public void onLoadingMore() {
                if (TextUtils.isEmpty(mLoadMoreDataUrl)){
                    Toast.makeText(mainActivity, "没有更多数据！", Toast.LENGTH_SHORT).show();
                    mLvContent.refreshFinish();
                }else {
                    Toast.makeText(mainActivity, "加载更多数据...", Toast.LENGTH_SHORT).show();
                    getDataFromNet(true);
                    mLvContent.refreshFinish();
                }

            }
        });
    }

    private void initNewsListView() {
        mNewsAdapter = new QuickAdapter<PageTagNewsData.NewsContent.ListNewsData>(
                mainActivity, R.layout.page_tag_list_item) {
            private ImageOptions.Builder mBannerImgBuider = new ImageOptions.Builder();

            @Override
            protected void convert(BaseAdapterHelper helper, int position,
                                   PageTagNewsData.NewsContent.ListNewsData item) {
                ImageView ivThumbnail = helper.getView(R.id.iv_thumbnail);
                // 通过xUtils3框架设置和加载网络图片
                x.image().bind(ivThumbnail, item.listimage,
                        getBannerImageOptions(
                                DensityUtil.dpToPx(100),
                                DensityUtil.dpToPx(80)));

                // 标题描述
                TextView tvTitle = helper.getView(R.id.tv_title);
                tvTitle.setText(item.title);

                // 发布日期
                TextView tvPubDate = helper.getView(R.id.tv_pubdate);
                tvPubDate.setText(item.pubdate);
            }

            private ImageOptions getBannerImageOptions(int imageWidth, int imageHeight) {
                // 设置默认图片
                mBannerImgBuider.setLoadingDrawableId(R.drawable.home_scroll_default);
                mBannerImgBuider.setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP);
                mBannerImgBuider.setSize(imageWidth, imageHeight);
                mBannerImgBuider.setUseMemCache(true);
                mBannerImgBuider.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
                return mBannerImgBuider.build();
            }
        };
        mLvContent.setAdapter(mNewsAdapter);
    }

    /**
     * 处理解析json数据
     *
     * @param jsonData ...
     */
    private PageTagNewsData parseJsonData(String jsonData) {
        // 解析json数据
        PageTagNewsData pageTagNewsData = mGson.fromJson(jsonData, PageTagNewsData.class);
        String more = pageTagNewsData.data.more;
        if (!TextUtils.isEmpty(more)) {
            mLoadMoreDataUrl = AppConst.NEWS_CENTER_URL + more;
        }
        return pageTagNewsData;
    }

    /**
     * 处理数据
     */
    private void processData(PageTagNewsData pageTagNewsData) {
        // 设置轮播图广告条的数据
        setBannerData(pageTagNewsData);

        // 设置轮播图的点指示器
        initPoints();

        // 设置轮播图的标题和选中的点
        setBannerImageTitleAndSelectPoint(mCurrentBannerIndex);

        // 轮播图自动轮播的处理
        bannerAutoScrollProcess();

        // 加载新闻列表数据
        loadNewsListData(pageTagNewsData);
    }

    private void loadNewsListData(PageTagNewsData pageTagNewsData) {
        if (pageTagNewsData.data.news == null || pageTagNewsData.data.news.isEmpty()) {
            return;
        }
        mNewsAdapter.replaceAll(pageTagNewsData.data.news);
    }

    private void bannerAutoScrollProcess() {
        if (mHandler == null) {
            mHandler = new Handler(Looper.getMainLooper());
        }
        //清空掉原来所有的任务
        mHandler.removeCallbacksAndMessages(null);
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 任务
                // 控制轮播图的显示
                int position = (mVpBanner.getCurrentItem() + 1) % mVpBanner.getAdapter().getCount();
                // 如果是最后一个item的时候，去除viewpager切换页面的动画
                mVpBanner.setCurrentItem(position, true);
                mHandler.postDelayed(this, BANNER_AUTO_SCROLL_DELAY_TIME);
            }
        }, BANNER_AUTO_SCROLL_DELAY_TIME);
    }

    private void setBannerImageTitleAndSelectPoint(int selectPosition) {
        // 获取轮播图标题描述数据
        String title = mTopNewsDatas.get(selectPosition).title;
        // 设置轮播图标题
        mTvBannerTitle.setText(title);

        // 设置轮播图选中的点
        for (int i = 0; i < mTopNewsDatas.size(); i++) {
            mLLPoints.getChildAt(i).setEnabled(i == selectPosition);
        }
    }

    private void initPoints() {
        // 清空原有的点视图
        mLLPoints.removeAllViews();

        for (int i = 0; i < mTopNewsDatas.size(); i++) {
            // 初始化点视图
            View vPoint = new View(mainActivity);
            vPoint.setBackgroundResource(R.drawable.point_selector);
            vPoint.setEnabled(false);
            int dp = 5;
            LinearLayout.LayoutParams params =
                    new LinearLayout.LayoutParams(
                            DensityUtil.dpToPx(dp),
                            DensityUtil.dpToPx(dp));
            params.leftMargin = DensityUtil.dpToPx(dp);
            if (i == 0) {
                vPoint.setEnabled(true);

            }
            vPoint.setLayoutParams(params);

            mLLPoints.addView(vPoint);
        }
    }

    private void setBannerData(PageTagNewsData pageTagNewsData) {
        mTopNewsDatas = pageTagNewsData.data.topnews;
        // 更新轮播图数据
        mBannerAdapter.notifyDataSetChanged();
    }

    private void getDataFromNet(boolean isLoadMore) {
        RequestParams params = new RequestParams("https://www.baidu.com/s");
        params.setMethod(HttpMethod.GET);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
//                2. 解析数据
                String testJson = "{\n" +
                        "    \"retcode\": 200,\n" +
                        "    \"data\": {\n" +
                        "        \"countcommenturl\": \"https://www.baidu.com\",\n" +
                        "        \"more\": \"https://www.baidu.com\",\n" +
                        "        \"title\": \"厦门\",\n" +
                        "        \"news\": [\n" +
                        "            {\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10001,\n" +
                        "                \"listimage\": \"http://img1.imgtn.bdimg.com/it/u=1603548911,2310384524&fm=23&gp=0.jpg\",\n" +
                        "                \"pubdate\": \"2016年02月12日 08:20:36\",\n" +
                        "                \"title\": \"Android开发大会\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10002,\n" +
                        "                \"listimage\": \"http://img4.imgtn.bdimg.com/it/u=3348217047,3111455891&fm=23&gp=0.jpg\",\n" +
                        "                \"pubdate\": \"2016年02月12日 08:20:36\",\n" +
                        "                \"title\": \"IOS开发大会\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10003,\n" +
                        "                \"listimage\": \"http://img4.imgtn.bdimg.com/it/u=549312016,4049119024&fm=23&gp=0.jpg\",\n" +
                        "                \"pubdate\": \"2016年02月12日 08:20:36\",\n" +
                        "                \"title\": \"WindowPhone开发大会\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },{\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10004,\n" +
                        "                \"listimage\": \"http://img2.imgtn.bdimg.com/it/u=1270641303,1899256283&fm=23&gp=0.jpg\",\n" +
                        "                \"pubdate\": \"2016年02月12日 08:20:36\",\n" +
                        "                \"title\": \"微信开发联盟大会\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },{\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10005,\n" +
                        "                \"listimage\": \"http://img1.imgtn.bdimg.com/it/u=3267626669,2039187042&fm=23&gp=0.jpg\",\n" +
                        "                \"pubdate\": \"2016年02月12日 08:20:36\",\n" +
                        "                \"title\": \"阿里年度盛典\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },{\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10006,\n" +
                        "                \"listimage\": \"http://img1.imgtn.bdimg.com/it/u=1662402735,2280034276&fm=23&gp=0.jpg\",\n" +
                        "                \"pubdate\": \"2016年02月12日 08:20:36\",\n" +
                        "                \"title\": \"可爱的小黄人\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"topic\": [\n" +
                        "            {\n" +
                        "                \"description\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10001,\n" +
                        "                \"listimage\": \"https://www.baidu.com\",\n" +
                        "                \"sort\": 1,\n" +
                        "                \"title\": \"全国两会在北京举行\",\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            }\n" +
                        "        ],\n" +
                        "        \"topnews\": [\n" +
                        "            {\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10001,\n" +
                        "                \"pubdate\": \"2016年03月03日 12:32:20\",\n" +
                        "                \"title\": \"厦门房价持续上升\",\n" +
                        "                \"topimage\": \"http://pic12.nipic.com/20110217/6757620_105953632124_2.jpg\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },{\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10002,\n" +
                        "                \"pubdate\": \"2016年03月02日 11:05:26\",\n" +
                        "                \"title\": \"太空冲击波\",\n" +
                        "                \"topimage\": \"http://www.deskcar.com/desktop/else/201298121023/8.jpg\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },{\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10003,\n" +
                        "                \"pubdate\": \"2016年02月28日 09:15:52\",\n" +
                        "                \"title\": \"美丽的风景\",\n" +
                        "                \"topimage\": \"http://img.win7china.com/NewsUploadFiles/20090823_121319_375_u.jpg\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            },\n" +
                        "            {\n" +
                        "                \"comment\": true,\n" +
                        "                \"commentlist\": \"https://www.baidu.com\",\n" +
                        "                \"commenturl\": \"https://www.baidu.com\",\n" +
                        "                \"id\": 10004,\n" +
                        "                \"pubdate\": \"2016年02月22日 13:22:35\",\n" +
                        "                \"title\": \"尼康D700可爱的小猫\",\n" +
                        "                \"topimage\": \"http://www.pp3.cn/uploads/allimg/111118/10562Cb5-13.jpg\",\n" +
                        "                \"type\": 1,\n" +
                        "                \"url\": \"https://www.baidu.com\"\n" +
                        "            }\n" +
                        "        ]\n" +
                        "        }\n" +
                        "    }";
                // 保存数据到本地
                SharedPUtil.setString(mainActivity, AppConst.NEWSNEWSCENTERCACHE, testJson);
                // 解析数据
                PageTagNewsData pageTagNewsData = parseJsonData(testJson);
                processData(pageTagNewsData);
                if (isRefresh) {
                    mLvContent.refreshFinish();
                    Toast.makeText(mainActivity, "刷新数据成功!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                // 请求失败
                if (isRefresh) {
                    mLvContent.refreshFinish();
                    Toast.makeText(mainActivity, "刷新数据失败!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                if (isRefresh) {
                    mLvContent.refreshFinish();
                    Toast.makeText(mainActivity, "刷新数据取...!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFinished() {
            }
        });
    }

    public View getRootView() {
        return mRootView;
    }

    private class BannerAdapter extends PagerAdapter {
        private ImageOptions.Builder mBannerImgBuider;

        public BannerAdapter() {

        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // 获取图片地址数据
            String imageUrl = mTopNewsDatas.get(position).topimage;
            ImageView imageView = new ImageView(mainActivity);

            // 通过xUtils3框架设置和加载网络图片
            x.image().bind(imageView, imageUrl,
                    getBannerImageOptions(
                            DensityUtil.getScreenWidth(mainActivity),
                            DensityUtil.dpToPx(200)));
            container.addView(imageView);
            return imageView;
        }

        private ImageOptions getBannerImageOptions(int imageWidth, int imageHeight) {
            if (mBannerImgBuider == null) {
                mBannerImgBuider = new ImageOptions.Builder();
            }
            // 设置默认图片
            mBannerImgBuider.setLoadingDrawableId(R.drawable.home_scroll_default);
            mBannerImgBuider.setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP);
            mBannerImgBuider.setSize(imageWidth, imageHeight);
            mBannerImgBuider.setUseMemCache(true);
            mBannerImgBuider.setImageScaleType(ImageView.ScaleType.CENTER_CROP);
            return mBannerImgBuider.build();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return mTopNewsDatas.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return object == view;
        }
    }
}
