package com.iplayer.basiclib.base;

import android.app.Application;

import com.iplayer.basiclib.util.Utils;

import java.util.Stack;

/**
 * Created by Administrator on 2017/9/19.
 */

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Utils.init(this);
    }

}
