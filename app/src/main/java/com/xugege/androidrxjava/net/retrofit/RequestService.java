package com.xugege.androidrxjava.net.retrofit;


import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface RequestService {
    @GET
    Call<String> get(@Url String url, @QueryMap Map<String,Object> fieldMap);
}
