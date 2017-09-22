package com.iplayer.basiclib.util;

import android.app.Activity;
import android.os.Process;

import java.util.Iterator;
import java.util.Stack;

/**
 * Created by Administrator on 2017/9/22.
 */

public class ActivityManagerUtils {
    private static ActivityManagerUtils instance;
    private Stack<Activity> activityStack = new Stack();

    public ActivityManagerUtils() {
    }

    public static ActivityManagerUtils getInstance() {
        if(instance == null) {
            instance = new ActivityManagerUtils();
        }

        return instance;
    }

    /**
     * 获取当前Activity
     */
    public Activity getCurrentActivity() {
        return activityStack.lastElement();
    }

    public void pushOneActivity(Activity actvity) {
        this.activityStack.add(actvity);
    }

    public void popOneActivity(Activity activity) {
        if(this.activityStack != null && this.activityStack.size() > 0 && activity != null) {
            this.activityStack.remove(activity);
            activity.finish();
        }

    }

    /**
     * 结束当前Activity
     */
    public void finishActivity(Activity activity) {
        if(activity != null) {
            this.activityStack.remove(activity);
            activity.finish();
        }
    }


    /**
     * 结束指定Class的Activity
     */
    public void finishActivity(Class<?> cls) {
        Iterator var2 = this.activityStack.iterator();

        while(var2.hasNext()) {
            Activity activity = (Activity)var2.next();
            if(activity.getClass().equals(cls)) {
                this.finishActivity(activity);
            }
        }

    }


    /**
     * 结束全部的Activity
     */
    public void finishAllActivity() {
        try {
            for(int i = 0; i < this.activityStack.size(); ++i) {
                if(null != this.activityStack.get(i)) {
                    (this.activityStack.get(i)).finish();
                }
            }

            this.activityStack.clear();
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }


    /**
     * 退出应用程序
     */
    public void appExit() {
        try {
            this.finishAllActivity();
            System.exit(0);
            Process.killProcess(Process.myPid());
        } catch (Exception var2) {
            var2.printStackTrace();
        }

    }
}
