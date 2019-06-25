package com.xugege.rxjava.content.utils.bus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.xugege.rxjava.R;

import io.reactivex.Observer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class RxbusActivity extends AppCompatActivity {
    private CompositeDisposable compositeDisposable;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rxbus);
        compositeDisposable = new CompositeDisposable();
        RxBus.getInstance().toObservable(MsgEvent.class).subscribe(new Observer<MsgEvent>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
                Toast.makeText(getApplicationContext(), "onSubscribe ", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNext(MsgEvent msgEvent) {
                Toast.makeText(getApplicationContext(), "onNext "+msgEvent.getMsg(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable e) {
                Toast.makeText(getApplicationContext(), "onError "+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onComplete() {
                Toast.makeText(getApplicationContext(), "onComplete ", Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable!=null && !compositeDisposable.isDisposed()){
            compositeDisposable.dispose();
        }
    }

    public void test(View view) {
        RxBus.getInstance().post(new MsgEvent("这个是测试"));
    }
}
