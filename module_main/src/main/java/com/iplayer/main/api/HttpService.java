package com.iplayer.main.api;

import com.iplayer.basiclib.http.Response;
import com.iplayer.main.model.HomeVideoList;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by Administrator on 2017/9/22.
 */

public interface HttpService {

    // 版本
    @GET("api/pecooAppVersion/queryAppVersion")
    Observable<Response> queryAppVersion();

    // 首页列表数据
    @GET("http://app.video.baidu.com/adnativeindex/")
    Observable<Response<List<HomeVideoList>>> queryHomeList(@Query("version") String version);

}
