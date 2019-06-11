package com.xugege.androidrxjava.retrofit;

import okhttp3.OkHttpClient;

public class HttpClient {
    private static HttpClient instance;

    public OkHttpClient.Builder getBuilder() {
        return builder;
    }

    private OkHttpClient.Builder builder;

    public HttpClient(){
        builder = new OkHttpClient.Builder();
    }

    public static HttpClient getInstance(){

        if (instance == null){
            synchronized ( HttpClient.class){
                if (instance != null){
                    instance = new HttpClient();
                }
            }
        }
        return instance;
    }



}
