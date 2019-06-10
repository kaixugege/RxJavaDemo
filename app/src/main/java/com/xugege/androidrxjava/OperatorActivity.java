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
import io.reactivex.functions.BiFunction;
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

    /**
     * 将连续的两个值进行计算后不发射出去，返回给接口当做下一个计算的第一个参数
     * 直到计算完毕之后再发射出去，所以 accept只会执行一次
     * @param view
     */
    public void reduceClick(View view) {
        Observable.range(1, 10).reduce(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                Log.d(TAG, "apply integer=" + integer + "  integer2=" + integer2);
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "reduceClick " + integer);
            }
        });

    }

    public void filterClick(View view) {
//        Observable.just(1, 2, 3, 5)
//                .filter(new BiFunction<Integer, Boolean>() {
//                    @Override
//                    public Object apply(Object o, Object o2) throws Exception {
//                        return null;
//                    }
//
//                    @Override
//                    public Boolean call(Integer integer) {
//                        // 限制条件
//                        return integer > 2;
//                    }
//                })
//                .subscribe(<Integer>() {
//
////
////                    @Override
////                    public void call(Integer integer) {
////                        Log.e("zpan", " filter =" + integer);
////                    }
//                });

    }


    public class CreateAction implements Observer<String> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(String s) {
            Log.d(TAG, "CreateAction  onNext  " + s);
        }


        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }


    /**
     * 将第一个元素和第二个元素进行计算，并发射出去，这个结果再次返回给 这个applly 的第一个参数，
     * 第二个参数就是list中的下一个值类似python的filter函数
     * 注意：每一次计算之后都会发射
     *
     *  @param view
     */
    public void scanClick(View view) {

        Observable.range(1, 10).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                Log.d(TAG, "apply integer=" + integer + "  integer2=" + integer2);
                return integer + integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.d(TAG, "scanClick " + integer);
            }
        });
    }



}
