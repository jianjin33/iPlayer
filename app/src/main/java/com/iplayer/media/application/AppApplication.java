package com.iplayer.media.application;

import com.iplayer.annotation.router.Components;
import com.iplayer.basiclib.base.BaseApplication;
import com.iplayer.compiler.arouter.ARouterHelper;
import com.iplayer.componentlib.router.Router;

/**
 * Created by Administrator on 2017/9/19.
 * 主工程Application
 */

@Components({"module_main"})
public class AppApplication extends BaseApplication {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouterHelper.install();
        Router.registerComponent("com.iplayer.main.applike.MainAppLike");
    }
}
