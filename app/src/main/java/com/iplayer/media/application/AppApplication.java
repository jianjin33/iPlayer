package com.iplayer.media.application;

import android.app.Application;

import com.iplayer.annotation.router.Components;
import com.iplayer.compiler.arouter.RouterHelper;
import com.iplayer.componentlib.router.Router;

/**
 * Created by Administrator on 2017/9/19.
 */

@Components({"module_main"})
public class AppApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RouterHelper.install();
        Router.registerComponent("com.iplayer.main.applike.MainAppLike");
    }
}
