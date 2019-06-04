package com.xugege.androidrxjava;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import io.reactivex.*;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import java.lang.annotation.Annotation;

public class Main2Activity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Log.i(TAG,"activity 线程："+android.os.Process.myTid());
//        init1();
        init2();
    }

    private void init2() {
        Observable.create(new ObservableOnSubscribe<String >() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                Log.i(TAG,"发射  1   线程："+android.os.Process.myTid());
                emitter.onNext("1");
                Log.i(TAG,"发射  2   线程："+android.os.Process.myTid());
                emitter.onNext("2");
                Log.i(TAG,"发射  3   线程："+android.os.Process.myTid());
                emitter.onNext("3");
                Log.i(TAG,"发射  onComplete   线程："+android.os.Process.myTid());
                emitter.onComplete();
            }
        })
                .observeOn(AndroidSchedulers.mainThread())//回调在主线程
                .subscribeOn(Schedulers.io())//执行在io线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "onSubscribe ");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.i(TAG, "onNext "+s);
                        Log.i(TAG,"onNext 线程："+android.os.Process.myTid());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError "+e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                        Log.i(TAG,"onComplete 线程："+android.os.Process.myTid());
                    }
                });

    }

    private void init1() {
        //三者的关系  创建被监听者，创建监听者，建立连接关系
//        这个是普通版本，在哪个线程里面产生就在哪个线程里面消费
        //创建被观察者
        Observable.create(new ObservableOnSubscribe() {
            @Override
            public void subscribe(ObservableEmitter emitter) throws Exception {
                Log.i(TAG, "开始发射 " + "连载1");
                emitter.onNext("连载1");
                Log.i(TAG, "开始发射 " + "连载2");
                emitter.onNext("连载2");
                Log.i(TAG, "开始发射 " + "连载3");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        }).subscribe(new Observer<String>() {
            //创建观察者
            @Override
            public void onSubscribe(Disposable d) {
                Log.i(TAG, "onSubscribe");
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext  " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                Log.i(TAG, "onComplete");
            }
        });


    }

    public void btClick(View view) {
        startActivity(new Intent(this, Main3Activity.class));

    }

    public String getThreadPid(){
       String threadPidName = Thread.currentThread().getName();
        return threadPidName;
    }
}
