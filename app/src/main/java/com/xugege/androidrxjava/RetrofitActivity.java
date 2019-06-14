package com.xugege.androidrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xugege.androidrxjava.net.retrofit.RequestService;
import com.xugege.androidrxjava.net.retrofit.RestServer;
import com.xugege.androidrxjava.net.retrofitrx.RxRestService;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
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


        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//日志级别
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();

        RestServer.INSTANCE()
                .setRetrofit(new Retrofit.Builder()
//                        .baseUrl("https://www.baidu.com/")
                        .baseUrl("https://blog.csdn.net")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .client(client)
                        .build());

    }

    public void requset(View view) {
        HashMap<String, Object> filedMap = new HashMap<String, Object>();
        requestService = RestServer.INSTANCE().getRetrofit().create(RequestService.class);
        requestService.get("", filedMap).enqueue(new Callback<String>() {

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


//        Retrofit.Builder retrofitBuilder = new Retrofit.Builder();
//        //配置回调库，采用rxjava
//        retrofitBuilder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
//        retrofitBuilder.baseUrl("https://www.baidu.com");
//
//
//        File cacheFile = new File(getApplication().getExternalCacheDir(), "HttpCache");//缓存地址
//        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50); //大小50Mb
//
//        //设置缓存方式、时长、地址
//
//        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
//        //启用 Log日志
//        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
//        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
//        okHttpClientBuilder.addInterceptor(loggingInterceptor);//添加拦截器
//
//        //设置请求超时时长为15秒
//        okHttpClientBuilder.connectTimeout(15, TimeUnit.SECONDS);
//
//
//        okHttpClientBuilder.addNetworkInterceptor(cacheIntercepter);
//        okHttpClientBuilder.addInterceptor(cacheIntercepter);
//        okHttpClientBuilder.cache(cache);


        final RxRestService rxRestService;
//        final Retrofit rrr = new Retrofit.Builder()
//                .baseUrl("https://www.baidu.com/")
//                .addConverterFactory(ScalarsConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();

        rxRestService = RestServer.INSTANCE().getRetrofit().create(RxRestService.class);
        HashMap<String, Object> filedMap = new HashMap<String, Object>();
        rxRestService.get("/weixin_33874713/article/details/87469630", filedMap)
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext " + s);
                    }


                    @Override
                    public void onError(Throwable e) {

                        if (e == null){
                            Log.e(TAG, "onError "+ "error = null"+e);
                        }
                        Log.e(TAG, "onError "+ e.getLocalizedMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete ");
                    }
                });

    }


}