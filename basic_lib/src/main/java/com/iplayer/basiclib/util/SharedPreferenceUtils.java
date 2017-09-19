package com.iplayer.basiclib.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.iplayer.basiclib.core.Constants;


/**
 * Created by jzm
 */
public class SharedPreferenceUtils {

    private static SharedPreferences sp;

    /**
     * 保存boolean信息
     */
    public static void saveBoolean(String key, boolean value) {
        //name : 保存信息的xml文件的名称
        //mode : 读写权限
        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        //保存操作
        sp.edit().putBoolean(key, value).apply();
    }

    /**
     * 获取保存的boolean值
     */
    public static boolean getBoolean(String key, boolean defValue) {
        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getBoolean(key, defValue);
    }

    /**
     * 保存String信息
     */
    public static void saveString(String key, String value) {

        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defValue) {
        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getString(key, defValue);
    }

    /***
     * 保存int信息
     */
    public static void saveInt(String key, int value) {
        //name : 保存信息的xml文件的名称
        //mode : 读写权限
        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        //保存操作
        sp.edit().putInt(key, value).apply();
    }

    /**
     * 获取保存的int值
     */
    public static int getInt(String key, int defValue) {
        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getInt(key, defValue);
    }

    /**
     * 保存long信息
     */
    public static void saveLong(String key, long value) {

        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        sp.edit().putLong(key, value).apply();
    }

    public static long getLong(String key, long defValue) {
        if (sp == null) {
            sp = Utils.getContext().getSharedPreferences(Constants.SP_NAME, Context.MODE_PRIVATE);
        }
        return sp.getLong(key, defValue);
    }

}
