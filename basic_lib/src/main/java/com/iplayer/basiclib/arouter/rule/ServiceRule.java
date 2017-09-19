package com.iplayer.basiclib.arouter.rule;

import android.app.Activity;

import com.iplayer.basiclib.arouter.exception.ServiceNotRouteException;


/**
 * Created by Administrator on 2017/7/25.
 * service路由
 */

public class ServiceRule extends BaseIntentRule<Activity> {

    /**
     * service路由scheme
     */
    public static final String SERVICE_SCHEME = "service://";

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwException(String pattern) {
        throw new ServiceNotRouteException(pattern);
    }
}
