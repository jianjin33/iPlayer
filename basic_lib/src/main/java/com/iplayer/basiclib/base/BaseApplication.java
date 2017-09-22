package com.iplayer.basiclib.base;

import android.app.Application;

import com.iplayer.basiclib.util.Utils;

/**
 * Created by Administrator on 2017/9/19.
 * Application 基类
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }

}
