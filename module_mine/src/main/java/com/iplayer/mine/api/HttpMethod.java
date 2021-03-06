package com.iplayer.mine.api;


import com.iplayer.basiclib.http.HttpClient;
import com.iplayer.basiclib.http.HttpResultFunc;
import com.iplayer.basiclib.util.LogUtils;
import com.trello.rxlifecycle.ActivityEvent;
import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentEvent;
import com.trello.rxlifecycle.FragmentLifecycleProvider;
import com.trello.rxlifecycle.LifecycleTransformer;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/25.
 */

public class HttpMethod {

    public static final String BASE_URL = "";

    private HttpMethod() {
    }

    private static HttpMethod httpMethod = new HttpMethod();

    private static HttpService service = new HttpClient<>(BASE_URL, HttpService.class).getService();

    public static HttpMethod getInstance() {
        LogUtils.d("httpMethod>>>", "service:" + service);
        return httpMethod;
    }


    /**
     * 用户登录
     */
    public <P> void login(Subscriber subscriber, P provider, String account, String pwd) {
        Observable observable = service.login(account, pwd)
                .map(new HttpResultFunc());

        toSubscribe(provider, observable, subscriber);
    }

    /**
     * 用户注册
     */
    public <P> void register(Subscriber subscriber, P provider, String account, String pwd) {
        Observable observable = service.register(account, pwd)
                .map(new HttpResultFunc());

        toSubscribe(provider, observable, subscriber);
    }

    /**
     * observeOn()方法将会在指定的调度器上返回结果：如在UI线程。
     */
    private <T, P> void toSubscribe(P provider, Observable<T> o, Subscriber s) {
        LifecycleTransformer transformer;
        if (provider instanceof ActivityLifecycleProvider) {
            transformer = ((ActivityLifecycleProvider) provider).bindUntilEvent(ActivityEvent.DESTROY);
        } else {
            transformer = ((FragmentLifecycleProvider) provider).bindUntilEvent(FragmentEvent.DESTROY);
        }

        o.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .compose(transformer)
                .subscribe(s);
    }
}
