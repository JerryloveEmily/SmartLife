package com.jerry.smartlife.fragment.tagpager;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.android.volley.VolleyError;
import com.jerry.smartlife.R;
import com.jerry.smartlife.fragment.base.BaseTagPager;
import com.jerry.smartlife.utils.JLog;
import com.jerry.smartlife.utils.network.VolleyRequestManager;
import com.jerry.smartlife.utils.network.request.GsonGetRequest;
import com.jerry.smartlife.utils.network.request.GsonPostRequest;
import com.jerry.smartlife.utils.network.request.ResponseListener;

import java.util.HashMap;
import java.util.Map;

/**
 * 首页
 * Created by JerryloveEmily on 16/2/28.
 */
public class HomePager extends BaseTagPager {

    private VolleyRequestManager requestManager;

    public HomePager(Context context) {
        super(context);
    }

    @Override
    public void initView() {
        super.initView();
    }

    @Override
    public void initData() {
        super.initData();
        mTvTitle.setText("首页");
        // 隐藏打开侧边栏按钮
        mIbtnMenu.setVisibility(View.GONE);
        View testView = View.inflate(mContext, R.layout.layout_test, null);
        Button button = (Button) testView.findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发起请求
                requestManager = VolleyRequestManager.getInstance(mContext);
                String url = "http://httpbin.org/gzip";
                GsonGetRequest request = new GsonGetRequest<String>(url, String.class,
                        new ResponseListener<String>() {
                            @Override
                            public void onResponse(String response) {
                                super.onResponse(response);
                                JLog.e("response: " + response);
                            }

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                super.onErrorResponse(error);
                            }
                        });
                requestManager.addRequestToQueue(request);
            }
        });

        Button button2 = (Button) testView.findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 发起请求
                requestManager = VolleyRequestManager.getInstance(mContext);
                String url = "http://httpbin.org/post";
                Map<String, String> params = new HashMap<>();
                params.put("userName", "Jerry");
                params.put("password", "123456");
                GsonPostRequest request = new GsonPostRequest<String>(url, String.class,
                        params, new ResponseListener<String>(){
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        super.onErrorResponse(error);
                    }

                    @Override
                    public void onResponse(String response) {
                        super.onResponse(response);
                    }
                });
                requestManager.addRequestToQueue(request);
            }
        });

        mFlContent.addView(testView);
    }
}
