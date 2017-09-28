package com.iplayer.basiclib.base;

import android.content.Context;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

/**
 * Created by Administrator on 2017/9/22.
 */

public abstract class BasePresenter {
    protected Context mContext;

    public BasePresenter(Context context) {
        this.mContext = context;
    }

    public void doDestroy() {
        this.mContext = null;
    }

    protected ActivityLifecycleProvider getActivityLifecycleProvider() {
        ActivityLifecycleProvider provider = null;
        if (null != this.mContext && this.mContext instanceof ActivityLifecycleProvider) {
            provider = (ActivityLifecycleProvider) this.mContext;
        }

        return provider;
    }

}
