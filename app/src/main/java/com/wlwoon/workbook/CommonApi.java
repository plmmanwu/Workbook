package com.wlwoon.workbook;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
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
}
