package com.iplayer.basiclib.arouter.rule;

import android.app.Activity;

import com.iplayer.basiclib.arouter.exception.ReceiverNotRouteException;


/**
 * Created by Administrator on 2017/7/25.
 * receiver路由
 */

public class ReceiverRule extends BaseIntentRule<Activity> {

    /**
     * receiver路由scheme
     */
    public static final String RECEIVER_SCHEME = "receiver://";

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwException(String pattern) {
        throw new ReceiverNotRouteException("receiver", pattern);
    }
}