package com.iplayer.basiclib.base;

import android.content.Context;

import com.trello.rxlifecycle.ActivityLifecycleProvider;

import rx.Observable;
import rx.Observer;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/22.
 * MVP M基类
 */

public class BaseModel {
    protected Context mContext;

    public BaseModel(Context context) {
        this.mContext = context;
    }

    protected ActivityLifecycleProvider getActivityLifecycleProvider() {
        ActivityLifecycleProvider provider = null;
        if(null != this.mContext && this.mContext instanceof ActivityLifecycleProvider) {
            provider = (ActivityLifecycleProvider)this.mContext;
        }

        return provider;
    }

//    public Observable subscribe(Observable mObservable, Observer observer) {
//        mObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).compose(this.getActivityLifecycleProvider().bindUntilEvent(ActivityEvent.DESTROY)).subscribe(observer);
//        return mObservable;
//    }
}
