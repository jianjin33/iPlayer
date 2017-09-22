package com.iplayer.basiclib.http;

import com.google.gson.JsonParseException;
import com.iplayer.basiclib.util.LogUtils;

import org.json.JSONException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.text.ParseException;

import rx.Subscriber;


/**
 * 用于在Http请求开始时
 * 在Http请求结束 调用者自己对请求数据进行处理
 */
public class HttpSubscriber<T> extends Subscriber<T> {

    private HttpCallback callback;

    public HttpSubscriber(HttpCallback callback) {
        this.callback = callback;
    }

    /**
     * 订阅开始时调用
     */
    @Override
    public void onStart() {
    }

    /**
     * 完成
     */
    @Override
    public void onCompleted() {
    }

    /**
     * 对错误进行统一处理
     */
    @Override
    public void onError(Throwable e) {
        try {
            if (e instanceof SocketTimeoutException) {
//                ToastUtil.showToast("网络有点慢，请稍后再试");
            } else if (e instanceof ConnectException) {
//                ToastUtil.showToast("网络中断");
            } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
//                ToastUtil.showToast("Error:1003");
                LogUtils.e("错误", e.getMessage() + "均视为解析错误");
            } else if (e.toString().contains("retrofit2.adapter.rxjava.HttpException")) {
//                ToastUtil.showToast("服务器连接失败");
                LogUtils.e("api错误", "没有该接口" + e.getMessage());
            } else if (e.toString().contains("com.google.gson.JsonSyntaxException")) {
                LogUtils.e("类型转换错误", e.getMessage());
            } else {
                LogUtils.e("其他错误", "error" + e.getMessage());
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            if (!this.isUnsubscribed()) {
                this.unsubscribe();
            }

            // 网络加载错误对外提供的接口
            if (callback != null) {
                callback.onError(e);
            }
        }
    }

    /**
     * 将onNext方法中的返回结果交给Activity或Fragment自己处理
     */
    @Override
    public void onNext(T t) {
        if (callback != null) {
            callback.onNext(t);
        }

    }

}