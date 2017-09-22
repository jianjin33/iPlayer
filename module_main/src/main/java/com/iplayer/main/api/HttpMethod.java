package com.iplayer.main.api;


import com.iplayer.basiclib.http.HttpClient;
import com.iplayer.basiclib.http.HttpResultFunc;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/7/27.
 */

public class HttpMethod {

    public static final String BASE_URL = "";

    private HttpMethod() {
    }

    private static HttpMethod httpMethod = new HttpMethod();

    public static HttpMethod getInstance() {
        return httpMethod;
    }


    private HttpService service = new HttpClient<>(BASE_URL,HttpService.class).getService();


    /**
     * 注册发送验证码
     */
    public void queryAppVersion(Subscriber subscriber) {
        Observable observable = service.queryAppVersion()
                .map(new HttpResultFunc());

        toSubscribe(observable, subscriber);
    }

    /**
     * observeOn()方法将会在指定的调度器上返回结果：如在UI线程。
     * onBackpressureBuffer()方法将告诉Observable发射的数据如果比观察者消费的数据要更快的话，它必须把它们存储在缓存中并提供一个合适的时间给它们。
     * 避免出现Only the original thread that created a view hierarchy can touch its views.
     * <p>
     * Can't create handler inside thread that has not called Looper.prepare()
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
