package com.jerry.smartlife.fragment.newscenter.pagetag;

import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jerry.smartlife.R;
import com.jerry.smartlife.activity.MainActivity;
import com.jerry.smartlife.app.AppConst;
import com.jerry.smartlife.bean.NewsCenterData;
import com.jerry.smartlife.bean.PageTagNewsData;
import com.jerry.smartlife.utils.DensityUtil;
import com.jerry.smartlife.utils.SharedPUtil;
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
    @Bind(R.id.vp_banner)
    ViewPager mVpBanner;        // 轮播图广告条
    @Bind(R.id.tv_title)
    TextView mTvBannerTitle;    // 轮播图描述
    @Bind(R.id.ll_points)
    LinearLayout mLLPoints;     // 轮播图点的指示容器
    @Bind(R.id.lv_content)
    ListView mLvContent;        // 新闻内容列表

    private ViewPagerScroller mBannerScroller;
    private BannerAdapter mBannerAdapter;
    private NewsCenterData.NewsData.ViewTagData mViewTagData;
    private Gson mGson;
    private Handler mHandler = new Handler(Looper.getMainLooper());

    private List<PageTagNewsData.NewsContent.TopNewsData> mTopNewsDatas = new ArrayList<>();

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
    }

    private void initData() {
        // 设置轮播图的适配器
        mBannerAdapter = new BannerAdapter();
        mVpBanner.setAdapter(mBannerAdapter);
        // 修改viewpager滚动一页的时间,默认是1秒
        mBannerScroller = new ViewPagerScroller(mainActivity);
        mBannerScroller.initViewPagerScroller(mVpBanner);
        // 获取本地缓存数据
        String jsonCache = SharedPUtil.getString(mainActivity, AppConst.NEWSNEWSCENTERCACHE, "");
        if (!TextUtils.isEmpty(jsonCache)) {
            // 有缓存数据，处理数据
            PageTagNewsData pageTagNewsData = parseJsonData(jsonCache);
            processData(pageTagNewsData);
        }
        // 从网络获取数据
        getDataFromNet();
    }

    /**
     * 处理解析json数据
     *
     * @param jsonData ...
     */
    private PageTagNewsData parseJsonData(String jsonData) {
        // 解析json数据
        return mGson.fromJson(jsonData, PageTagNewsData.class);
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
        setBannerImageTitleAndSelectPoint(0);

        // 轮播图自动轮播的处理
        bannerAutoScrollProcess();
    }

    private void bannerAutoScrollProcess() {
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // 任务
                // 控制轮播图的显示
                int position = (mVpBanner.getCurrentItem() + 1) % mVpBanner.getAdapter().getCount();
                // 如果是最后一个item的时候，去除viewpager切换页面的动画
                mVpBanner.setCurrentItem(position, position != 0);
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

    private void getDataFromNet() {
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
                        "                \"listimage\": \"https://www.baidu.com\",\n" +
                        "                \"pubdate\": \"2016年02月12日 08:20:36\",\n" +
                        "                \"title\": \"Android开发大会\",\n" +
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
                parseJsonData(testJson);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                // 请求失败
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
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
                setBannerImageTitleAndSelectPoint(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

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
