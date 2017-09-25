package com.iplayer.mine.api;


import com.iplayer.basiclib.http.HttpClient;
import com.iplayer.basiclib.http.HttpResultFunc;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/9/25.
 */

public class HttpMethod {

    public static final String BASE_URL = "";

    private HttpMethod() {
    }

    private static HttpMethod httpMethod = new HttpMethod();

    public static HttpMethod getInstance() {
        return httpMethod;
    }


    private HttpService service = new HttpClient<>(BASE_URL, HttpService.class).getService();


    /**
     * 用户登录
     */
    public void login(Subscriber subscriber, String account, String pwd) {
        Observable observable = service.login(account, pwd)
                .map(new HttpResultFunc());

        toSubscribe(observable, subscriber);
    }
    /**
     * 用户注册
     */
    public void register(Subscriber subscriber, String account, String pwd) {
        Observable observable = service.register(account, pwd)
                .map(new HttpResultFunc());

        toSubscribe(observable, subscriber);
    }

    /**
     * observeOn()方法将会在指定的调度器上返回结果：如在UI线程。
     */
    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.computation())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}
