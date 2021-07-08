package com.wlwoon.workbook;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by wxy on 2020/7/17.
 */

public interface CommonApi {


    @GET("api/data/Android/10/1")
    Observable<DemoData> getData();

    @POST("sdw/search/mutualmarket_c.aspx")
    Observable<String> getShareData(@Query("t") String t, @Body RequestBody requestBody);//t=sh  sz

    @POST("send")
    @Headers({"Content-type:application/json;charset=UTF-8"})
    Observable<String> sendHookMsg(@Query("access_token" )String token,@HeaderMap Map<String,String> map,@Body String requestBody);//t=sh  sz

    @POST("send")
    @Headers({"Content-type:application/json;charset=UTF-8"})
    Observable<String> sendHookMsg(@Query("access_token" )String token,@Body String requestBody);//t=sh  sz
}
