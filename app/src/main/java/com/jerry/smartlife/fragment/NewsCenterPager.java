package com.jerry.smartlife.fragment;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jerry.smartlife.activity.MainActivity;
import com.jerry.smartlife.bean.NewsCenterData;
import com.jerry.smartlife.fragment.base.BaseTagPager;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 首页
 * Created by JerryloveEmily on 16/2/28.
 */
public class NewsCenterPager extends BaseTagPager {

    public static final String TAG = "NewsCenterPager";

    public NewsCenterPager(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        // 获取网络数据
        RequestParams params = new RequestParams("https://www.baidu.com/s");
        params.setMethod(HttpMethod.GET);
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(x.app(), result, Toast.LENGTH_SHORT).show();
                String testJson = "{\"retCode\":200,\"data\":[{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"新闻\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"专题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"主题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"互动\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10001\",\"title\":\"北京\",\"type\":1,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         },{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"新闻\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"专题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"主题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"互动\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10002\",\"title\":\"中国\",\"type\":1,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         },{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"新闻\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"专题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"主题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"互动\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10003\",\"title\":\"国际\",\"type\":1,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         },{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"新闻\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"专题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"主题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"互动\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10004\",\"title\":\"体育\",\"type\":1,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         },{\n" +
                        "            \"children\":[\n" +
                        "            {\"id\":\"1001\",\"title\":\"新闻\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1002\",\"title\":\"专题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1003\",\"title\":\"主题\",\"type\":1,\"url\":\"http://www.baidu.com\"},\n" +
                        "            {\"id\":\"1004\",\"title\":\"互动\",\"type\":1,\"url\":\"http://www.baidu.com\"}\n" +
                        "            ],\n" +
                        "            \"id\":\"10005\",\"title\":\"教育\",\"type\":1,\"url\":\"http://www.baidu.com\",\n" +
                        "            \"url1\":\"http://www.baidu.com\",\"dayurl\":\"http://www.baidu.com\",\n" +
                        "            \"excurl\":\"http://www.baidu.com\",\"weekurl\":\"http://www.baidu.com\"\n" +
                        "         }], \n" +
                        "         \"extend\":[\n" +
                        "         \"1\",\"2\",\"3\",\"4\",\"5\"\n" +
                        "         ]\n" +
                        "         }";
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

        mTvTitle.setText("新闻中心");
        TextView tv = new TextView(mContext);
        tv.setText("新闻中心的内容");
        tv.setTextSize(22);
        tv.setTextColor(Color.DKGRAY);
        tv.setGravity(Gravity.CENTER);
        mFlContent.addView(tv);

    }

    /**
     * 解析json数据
     * @param result    ...
     */
    private void parseData(String result){
        Gson gson = new Gson();
        NewsCenterData newsCenterData = gson.fromJson(result, NewsCenterData.class);
        Log.e(TAG, "parseData: " + newsCenterData.data.get(0).children.get(0).title);
        ((MainActivity)mContext).getLeftMenuFragment().setLeftMenuData(newsCenterData.data);
    }
}
