package com.iplayer.media.application;

import android.app.Application;

import com.iplayer.componentlib.router.Router;

/**
 * Created by Administrator on 2017/9/19.
 */

public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Router.registerComponent("com.iplayer.main.applike.MainAppLike");
    }
}
