package com.iplayer.basiclib.arouter.exception;

/**
 * Created by Administrator on 2017/7/25.
 */

public class NotRouteException  extends RuntimeException {

    public NotRouteException(String name, String pattern) {
        super(String.format("%s cannot be resolved with pattern %s, have you declared it in your ARouter?", name, pattern));
    }
}
