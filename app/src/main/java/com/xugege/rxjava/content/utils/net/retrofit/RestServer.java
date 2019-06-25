package com.xugege.rxjava.content.utils.net.retrofit;

import retrofit2.Retrofit;

public class RestServer {

    public Retrofit getRetrofit() {
        return retrofit;
    }

    public RestServer setRetrofit(Retrofit retrofit) {
        this.retrofit = retrofit;
        return this;
    }

    private Retrofit retrofit;


    public RequestService getRequestService() {
        return requestService;
    }

    public RestServer setRequestService(RequestService requestService) {
        this.requestService = requestService;
        return this;
    }

    private RequestService requestService;

    private RestServer() {

    }

    public static RestServer INSTANCE() {
        return Holder.instance;
    }


    private static class Holder {
        private static RestServer instance = new RestServer();
    }


}
