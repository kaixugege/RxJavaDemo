package com.xugege.androidrxjava.retrofit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface RequestService {
    @GET
    Call<String> get(@Url String url, @FieldMap HashMap<Object,Object> fieldMap);
}
