package com.iplayer.basiclib.util;

/**
 * Created by Administrator on 2017/7/21.
 * 获取屏幕的宽 高
 * dp2px px2dp
 */

public class UIUtils {

    /**
     * 把dp转换成px
     **/
    public static int dp2px(int dp) {
        // px = dp * 密度比  0.75 1 1.5 2
        float density = Utils.getContext().getResources().getDisplayMetrics().density;// 获取手机对应的密度比
        return (int) (density * dp + 0.5);// 1 * 0.75
    }

    /**
     * 将 sp 转换为 px，
     */
    public static int sp2px(float pxValue) {
        final float fontScale = Utils.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue * fontScale + 0.5f);
    }

    /**
     * 获取屏幕的宽度
     * @return 屏幕宽度
     */
    public static int getScreenWidth(){
        return Utils.getContext().getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取屏幕的高度
     * @return 屏幕高度
     */
    public static int getScreenHeight(){
        return Utils.getContext().getResources().getDisplayMetrics().heightPixels;
    }
}
