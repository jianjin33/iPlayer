package com.iplayer.main.applike;

import com.iplayer.componentlib.applicationlike.IApplicationLike;
import com.iplayer.componentlib.router.Router;
import com.iplayer.componentservice.main.MainService;
import com.iplayer.main.serviceimpl.MainServiceImpl;

/**
 * Created by jianzuming on 17/9/17.
 */

public class MainAppLike implements IApplicationLike {
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        router.addService(MainService.class.getSimpleName(),new MainServiceImpl());
    }

    @Override
    public void onStop() {
        router.removeService(MainService.class.getSimpleName());
    }
}
