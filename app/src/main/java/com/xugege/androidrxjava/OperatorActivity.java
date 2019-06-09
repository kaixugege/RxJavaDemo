package com.xugege.androidrxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class OperatorActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
    }

    public void justClick(View view) {
        Observable.just("A").subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "just " + s);
            }
        });
    }

    public void fromArrayClick(View view) {
        String[] sss = {"a", "b", "c"};
        Observable.fromArray(sss).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                Log.d(TAG, "from " + s);
            }
        });
    }

    public void mapClick(View view) {
        String[] sss = {"a", "b", "c"};
        Observable.fromArray(sss).flatMap(new Function<String, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(String s) throws Exception {
                return null;
            }
        });
    }

    public void createClick(View view) {


        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {

                String[] sss = {"1", "2", "3"};
                for (String s : sss) {
                    emitter.onNext(s);
                }

            }
        }).subscribe(new CreateAction());


    }

    public void scanClick(View view) {

//        Observable.range(1,10).scan(new Function<Integer,Integer,Integer>() {
//            @Override
//            public Integer apply(Integer integer) throws Exception {
//                return null;
//            }
//        });



    }

    public class  CreateAction implements Observer<String>  {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.d(TAG,"CreateAction  onNext  "+s);
        }


        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }
}
