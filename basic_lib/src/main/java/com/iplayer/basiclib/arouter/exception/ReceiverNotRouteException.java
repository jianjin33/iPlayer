package com.iplayer.basiclib.arouter.exception;

/**
 * Created by Administrator on 2017/7/25.
 */

public class ReceiverNotRouteException extends NotRouteException {
    public ReceiverNotRouteException(String name, String pattern) {
        super(name, pattern);
    }
}
