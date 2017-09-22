package com.iplayer.basiclib.http;

public interface HttpCallback<T> {

    void onNext(T t);

    void onError(Throwable e);

}
