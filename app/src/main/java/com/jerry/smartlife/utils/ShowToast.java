/*
 * Copyright (c) 2014, 青岛司通科技有限公司 All rights reserved.
 * File Name：ShowToast.java
 * Version：V1.0
 * Author：zhaokaiqiang
 * Date：2014-8-7
 */
package com.jerry.smartlife.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @author zhaokaiqiang
 * @ClassName: com.drd.piaojubao.utils.ShowToast
 * @Description: 显示Toast的工具类
 * @date 2014-8-7 上午11:21:48
 */
public class ShowToast {

    /**
     * @param
     * @return void
     * @throws
     * @Description: 显示短时间Toast
     */
    public static void Short(Context context, CharSequence sequence) {
        Toast.makeText(context, sequence, Toast.LENGTH_SHORT).show();
    }

    /**
     * @param
     * @return void
     * @throws
     * @Description: 显示长时间Toast
     */
    public static void Long(Context context, CharSequence sequence) {
        Toast.makeText(context, sequence, Toast.LENGTH_LONG).show();
    }

    /**
     * 显示在中间,短时间
     * @param sequence
     */
    public static void centerShort(Context context, CharSequence sequence){
        Toast toast = Toast.makeText(context, sequence, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

    /**
     * 显示在中间,长时间
     * @param sequence
     */
    public static void centerLong(Context context, CharSequence sequence){
        Toast toast = Toast.makeText(context, sequence, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();
    }

}
