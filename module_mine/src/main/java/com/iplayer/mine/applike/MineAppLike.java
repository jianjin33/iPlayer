package com.iplayer.mine.applike;

import com.iplayer.componentlib.applicationlike.IApplicationLike;
import com.iplayer.componentlib.router.Router;
import com.iplayer.componentservice.main.MineService;
import com.iplayer.mine.serviceimpl.MineServiceImpl;

/**
 * Created by jianzuming on 17/9/17.
 */

public class MineAppLike implements IApplicationLike {
    Router router = Router.getInstance();
    @Override
    public void onCreate() {
        router.addService(MineService.class.getSimpleName(),new MineServiceImpl());
    }

    @Override
    public void onStop() {
        router.removeService(MineService.class.getSimpleName());
    }
}
