package com.jerry.smartlife.utils.network.request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.jerry.smartlife.utils.JLog;
import com.jerry.smartlife.utils.network.compress.GzipUtil;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * Volley通过Gson解析的Get方式网络请求
 * Created by Jerry on 2015/5/4.
 */
public class GsonGetRequest<T> extends Request<T> {

    private static final String TAG = "GetObjectRequest";

    private Gson mGson;
    private Response.Listener<T> mListener;
    private Type mClazz;

    public GsonGetRequest(String url, Class clazz, ResponseListener<T> listener) {
        super(Method.GET, url, listener);
        mListener = listener;
        mClazz = clazz;
        mGson = new Gson();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Charset", "UTF-8");
        headers.put("Content-Type", "application/json");
        headers.put("Accept-Encoding", "gzip,deflate");
        return headers;
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {
            // 首先判断是否使用Gzip数据压缩，来获取真实的数据
            Map<String, String> headers = response.headers;
            //遍历map中的键
            for (String key : headers.keySet()) {
                JLog.e("key = " + key + ", Value = " + headers.get(key));
            }

            /*String jsonData = new String(
                    response.data, HttpHeaderParser.parseCharset(response.headers));*/
            String jsonData = GzipUtil.getRealString(response.data);
            JLog.e("jsonString = " + jsonData);
            T result = mGson.fromJson(jsonData, mClazz);
            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(T response) {
        mListener.onResponse(response);
    }
}
