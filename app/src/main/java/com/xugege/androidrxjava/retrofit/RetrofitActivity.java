package com.xugege.androidrxjava.retrofit;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.xugege.androidrxjava.R;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

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
                        .build());

    }

    public void requset(View view) {
        HashMap<Object, Object> filedMap = new HashMap<Object, Object>();
        requestService = RestServer.INSTANCE().getRetrofit().create(RequestService.class);
        requestService.get("", filedMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.i(TAG, "onResponse " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.e(TAG, "onFailure  " + t.getMessage());
            }
        });
    }
}
