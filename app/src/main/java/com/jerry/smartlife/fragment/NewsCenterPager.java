package com.jerry.smartlife.fragment;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.smartlife.activity.MainActivity;
import com.jerry.smartlife.bean.NewsCenterData;
import com.jerry.smartlife.fragment.base.BaseTagPager;
import com.jerry.smartlife.fragment.newscenter.BaseNewsCenterPage;
import com.jerry.smartlife.fragment.newscenter.InteractBaseNewsCenterPage;
import com.jerry.smartlife.fragment.newscenter.NewsBaseNewsCenterPage;
import com.jerry.smartlife.fragment.newscenter.PhotosBaseNewsCenterPage;
import com.jerry.smartlife.fragment.newscenter.TopicBaseNewsCenterPage;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页
 * Created by JerryloveEmily on 16/2/28.
 */
public class NewsCenterPager extends BaseTagPager {

    public static final String TAG = "NewsCenterPager";

    private List<BaseNewsCenterPage> mNewsCenterPages = new ArrayList<>(5);
    private NewsCenterData mNewsCenterData;

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        // 1. 获取网络数据
        RequestParams params = new RequestParams("https://www.baidu.com/s");
        params.setMethod(HttpMethod.GET);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(), result, Toast.LENGTH_SHORT).show();
//                2. 解析数据
                String testJson = "{\"retCode\":200,\"data\":[{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"北京\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"中国\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"国际\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"体育\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1005\",\"title\":\"教育\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10001\",\"title\":\"新闻\",\"type\":1,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         },{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"北京\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"中国\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"国际\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"体育\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1005\",\"title\":\"教育\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10002\",\"title\":\"专题\",\"type\":2,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         },{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"北京\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"中国\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"国际\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"体育\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1005\",\"title\":\"教育\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10003\",\"title\":\"组图\",\"type\":3,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         },{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"北京\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"中国\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"国际\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"体育\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1005\",\"title\":\"教育\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10004\",\"title\":\"互动\",\"type\":4,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         }],\n" +
                        "         \"extend\":[\"1\",\"2\",\"3\",\"4\",\"5\"]}";
                parseData(testJson);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 解析json数据
     * @param result    ...
     */
    private void parseData(String result){
        Gson gson = new Gson();
        mNewsCenterData = gson.fromJson(result, NewsCenterData.class);
        // 3. 处理数据
        Log.e(TAG, "parseData: " + mNewsCenterData.data.get(0).children.get(0).title);
        ((MainActivity)mContext).getLeftMenuFragment().setLeftMenuData(mNewsCenterData.data);

        // 解析数据获取新闻中心侧边栏的四个菜单对应的页面数据
        // 根据服务器的数据顺序 创建四个页面加入到容器中
        for (NewsCenterData.NewsData newsData :
                mNewsCenterData.data) {
            BaseNewsCenterPage newsCenterPage = null;
            switch (newsData.type){
                case 1: // 新闻菜单
                    newsCenterPage = new NewsBaseNewsCenterPage((MainActivity) mContext);
                    break;
                case 2: // 专题菜单
                    newsCenterPage = new TopicBaseNewsCenterPage((MainActivity) mContext);
                    break;
                case 3: // 组图菜单
                    newsCenterPage = new PhotosBaseNewsCenterPage((MainActivity) mContext);
                    break;
                case 4: // 互动菜单
                    newsCenterPage = new InteractBaseNewsCenterPage((MainActivity) mContext);
                    break;
            }
            mNewsCenterPages.add(newsCenterPage);
        }

        // 4. 显示数据，控制四个页面的显示，默认显示新闻菜单的内容
        switchPage(0);
    }

    @Override
    public void switchPage(int position){
        // 标题
        mTvTitle.setText(mNewsCenterData.data.get(position).title);

        // 移除原来的页面内容
        mFlContent.removeAllViews();

        // 具体的内容数据视图, 加入新闻中心ViewPager页面的视图容器中
        mFlContent.addView(mNewsCenterPages.get(position).getRootView());
    }
}
