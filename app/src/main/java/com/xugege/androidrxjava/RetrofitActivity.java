package com.xugege.androidrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xugege.androidrxjava.net.retrofit.RequestService;
import com.xugege.androidrxjava.net.retrofit.RestServer;
import com.xugege.androidrxjava.net.retrofitrx.RxRestService;

import java.util.HashMap;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
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
                        .baseUrl("https://www.baidu.com/")
//                        .baseUrl("https://blog.csdn.net")
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
        final RxRestService rxRestService;
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//日志级别
            builder.addInterceptor(loggingInterceptor);
        }
        OkHttpClient client = builder.build();
        final Retrofit rrr = new Retrofit.Builder()
                .baseUrl("https://www.baidu.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();

        rxRestService = RestServer.INSTANCE().getRetrofit().create(RxRestService.class);
        HashMap<String, Object> filedMap = new HashMap<String, Object>();
        rxRestService.get("", filedMap)
                .subscribeOn(Schedulers.io())//指定被观察者执行的线程
                .observeOn(AndroidSchedulers.mainThread())//指定观察者执行的线程
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.i(TAG, "accept " + s);
                    }
                });

    }


}