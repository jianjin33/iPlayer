package com.iplayer.mine.api;

import com.iplayer.basiclib.http.Response;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/25.
 * 我的 模块所有接口
 */

public interface HttpService {

    // 用户登录
    @GET("api/login")
    Observable<Response> login(@Query("account") String account, @Query("password") String pwd);

    // 用户注册
    @FormUrlEncoded
    @POST("api/register")
    Observable<Response> register(@Field("account") String account, @Field("password") String pwd);

}
