package com.jerry.smartlife.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.view.Display;
import android.view.WindowManager;

/**
 * @author Administrator
 * @创建时间 2015-7-4 下午2:55:47
 * @描述 TODO
 * @ svn提交者：$Author: gd $
 * @ 提交时间: $Date: 2015-07-04 14:57:46 +0800 (Sat, 04 Jul 2015) $
 * @ 当前版本: $Rev: 9 $
 */
public class DensityUtil {
    private static int screenWidth = 0;
    private static int screenHeight = 0;

    private DensityUtil() {
    }

    /**
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 把dp转换成px单位
     * @param dp ...
     * @return   ...
     */
    public static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }

    /**
     * 获取屏幕的高度
     *
     * @param c ...
     * @return ...
     */
    public static int getScreenHeight(Context c) {
        if (screenHeight == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenHeight = size.y;
        }

        return screenHeight;
    }

    /**
     * 获取屏幕的宽度
     *
     * @param c ...
     * @return ...
     */
    public static int getScreenWidth(Context c) {
        if (screenWidth == 0) {
            WindowManager wm = (WindowManager) c.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            screenWidth = size.x;
        }

        return screenWidth;
    }
}
