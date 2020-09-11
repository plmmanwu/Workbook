package com.wlwoon.workbook;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by wxy on 2020/7/17.
 */

public interface CommonApi {


    @GET("api/data/Android/10/1")
    Observable<DemoData> getData();
}
