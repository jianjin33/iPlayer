package com.iplayer.basiclib.arouter.rule;

import android.app.Activity;

import com.iplayer.basiclib.arouter.exception.ActivityNotRouteException;


/**
 * Created by Administrator on 2017/7/25.
 * activity路由规则
 */

public class ActivityRule extends BaseIntentRule<Activity> {

    /** activity路由scheme*/
    public static final String ACTIVITY_SCHEME = "activity://";

    /**
     * {@inheritDoc}
     */
    @Override
    public void throwException(String pattern) {
        throw new ActivityNotRouteException(pattern);
    }
}