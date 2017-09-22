package com.iplayer.main.api;

import com.iplayer.basiclib.http.Response;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/22.
 */

public interface HttpService {

    //版本
    @GET("api/pecooAppVersion/queryAppVersion")
    Observable<Response> queryAppVersion();

}
