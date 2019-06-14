package com.xugege.androidrxjava.net.retrofitrx;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * @Author: KaixuGege
 * Time:           2019/6/14
 * ProjectName:    RxJavaDemo
 * ClassName:
 * Info:
 */
public interface RxRestService {

    @GET
    Observable<String> get(@Url String url, @QueryMap Map<String, Object> parms);

}
