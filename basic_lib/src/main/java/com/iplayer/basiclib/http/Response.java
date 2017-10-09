package com.iplayer.basiclib.http;

/**
 * Created by Administrator on 2017/7/24.
 */

public class Response<T> {
    protected String code;
    protected String message;
    private T slices;

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getSlices() {
        return slices;
    }
}
