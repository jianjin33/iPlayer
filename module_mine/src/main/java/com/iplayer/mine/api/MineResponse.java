package com.iplayer.mine.api;

import com.iplayer.basiclib.http.Response;

/**
 * Created by Administrator on 2017/9/28.
 */

public class MineResponse<T> extends Response {
    private T list;

    public T getList() {
        return list;
    }
}
