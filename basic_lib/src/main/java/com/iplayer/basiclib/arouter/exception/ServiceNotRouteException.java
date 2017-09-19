package com.iplayer.basiclib.arouter.exception;

/**
 * Created by Administrator on 2017/7/25.
 */

public class ServiceNotRouteException extends NotRouteException {

    public ServiceNotRouteException(String pattern) {
        super("service", pattern);
    }
}