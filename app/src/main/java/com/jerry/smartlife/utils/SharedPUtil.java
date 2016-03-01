package com.jerry.smartlife.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.jerry.smartlife.app.AppConst;

/**
 * Created by JerryloveEmily on 16/2/27.
 */
public class SharedPUtil {

    public static void setBoolean(Context context, String key, boolean value){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.CACHEFILE, Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key, value).commit();
    }

    public static boolean getBoolean(Context context, String key, boolean defValue){
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.CACHEFILE, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defValue);
    }
}
