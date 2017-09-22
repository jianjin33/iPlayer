package com.iplayer.basiclib.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.iplayer.basiclib.util.Utils;

import java.util.Stack;

/**
 * Created by Administrator on 2017/9/19.
 */

public class BaseApplication extends Application {

    private Stack<Activity> activityStack;

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }

}
