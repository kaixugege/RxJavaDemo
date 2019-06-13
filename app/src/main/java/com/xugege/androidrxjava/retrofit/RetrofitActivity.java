package com.xugege.androidrxjava.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.xugege.androidrxjava.R;
import com.xugege.androidrxjava.net.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    RequestService requestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrofit);
        initRetrofit();
    }

    private void initRetrofit() {

        RestServer.INSTANCE()
                .setRetrofit(new Retrofit.Builder()
                        .baseUrl("https://www.baidu.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build());

    }

    public void requset(View view) {
        HashMap<String, Object> filedMap = new HashMap<String, Object>();
        requestService = RestServer.INSTANCE().getRetrofit().create(RequestService.class);
        requestService.get("https://www.baidu.com/", filedMap).enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                Log.i(TAG, "onResponse " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure  " + t.getMessage());
            }

        });
    }

    public void rxjavaRequest(View view) {


        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
        //配置回调库，采用rxjava
        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        retrofitBuilder.baseUrl("https://www.baidu.com");


        File cacheFile = new File(getApplication().getExternalCacheDir(), "HttpCache");//缓存地址
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //大小50Mb

        //设置缓存方式、时长、地址

        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        //启用 Log日志
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClientBuilder.addInterceptor(loggingInterceptor);//添加拦截器

        //设置请求超时时长为15秒
        okHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);


        okHttpClientBuilder.addNetworkInterceptor(cacheIntercepter);
        okHttpClientBuilder.addInterceptor(cacheIntercepter);
        okHttpClientBuilder.cache(cache);


    }


    Interceptor cacheIntercepter = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {

            //对request的设置用来指定有网/无网下所走的方式
            //对response的设置用来指定有网/无网下的缓存时长

            Request request = chain.request();
            if (!NetworkUtil.isNetWorkEnable(getApplicationContext())) {
                //无网络下强制使用缓存，无论缓存是否过期，此时该请求实际上不会被发出去
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }

            Response response = chain.proceed(request);

            if (NetworkUtil.isNetWorkEnable(getApplicationContext())) {
                //有网络下，超过1分钟则重新请求，否则直接使用缓存数据
                int maxAge = 60;

                String cacheControl = "public,max-age=" + maxAge;
                //当然如果你想在有网络的情况下都直接走网络，那么只需要
                //将其超时时间maxAge设为0即可
                return response.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma").build();
            } else {
                //无网络时直接取缓存数据，该缓存数据保存1周
                int maxStale = 60 * 60 * 24 * 7 * 1;  //1周


                return response.newBuilder().header("Cache-Control", "public,only-if-cached,max-stale=" + maxStale).removeHeader("Pragma").build();


            }


        }
    };


}