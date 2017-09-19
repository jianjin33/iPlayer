package com.iplayer.basiclib.util;

import android.util.Log;

/**
 * 管理Log
 * @author jzm
 */
public class LogUtils {
	private static boolean isDebug = Utils.isAppDebug(); //是否开发调试模式
	
	/**
	 * 打印d级别
	 * @param tag
	 * @param msg
	 */
	public static void d(String tag,String msg){
		if(isDebug){
			Log.d(tag, msg);
		}
	}
	
	/**
	 * 打印e级别
	 * @param tag
	 * @param msg
	 */
	public static void e(String tag,String msg){
		if(isDebug){
			Log.e(tag, msg);
		}
	}
	
	
	
	/**
	 * 打印d级别
	 * @param obj
	 * @param msg
	 */
	public static void d(Object obj,String msg){
		if(isDebug){
			Log.d(obj.getClass().getSimpleName(), msg);
		}
	}
	
	/**
	 * 打印e级别
	 * @param obj
	 * @param msg
	 */
	public static void e(Object obj,String msg){
		if(isDebug){
			Log.e(obj.getClass().getSimpleName(), msg);
		}
	}
	
}
