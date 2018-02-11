package com.yz.yikfrl.yizhilearning.rxjava2learning;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * https://www.jianshu.com/p/a93c79e9f689
 */
public class MainActivity extends Activity {

    private StringBuffer mRxOperatorsText = new StringBuffer();
    private static final String TAG = "Rxjava2Learning";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        create();
        map();
    }

    private void create(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1\n");
                Log.e(TAG, "Observable emit 1\n");
                e.onNext(1);

                mRxOperatorsText.append("Observable emit 2\n");
                Log.e(TAG, "Observable emit 2\n");
                e.onNext(2);

                mRxOperatorsText.append("Observable emit 3\n");
                Log.e(TAG, "Observable emit 3\n");
                e.onNext(3);

                e.onComplete();

                mRxOperatorsText.append("Observable emit 4\n");
                Log.e(TAG, "Observable emit 4\n");
                e.onNext(4);
            }
        }).subscribe(new Observer<Integer>() {
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d) {
                mRxOperatorsText.append("onSubscribe : " + d.isDisposed() + "\n");
                Log.e(TAG, "onSubscribe : " + d.isDisposed() + "\n");
                mDisposable = d;
            }

            @Override
            public void onNext(Integer integer) {
                mRxOperatorsText.append("onNext : value : " + integer + "\n");
                Log.e(TAG, "onNext : value : " + integer + "\n" );
                i++;
                if(i == 2){
                    mDisposable.dispose();
                    mRxOperatorsText.append("onNext : isDisposed : " + mDisposable.isDisposed() + "\n");
                    Log.e(TAG, "onNext : isDisposed : " + mDisposable.isDisposed() + "\n");
                }
            }

            @Override
            public void onError(Throwable e) {
                mRxOperatorsText.append("onError : value : " + e.getMessage() + "\n");
                Log.e(TAG, "onError : value : " + e.getMessage() + "\n" );
            }

            @Override
            public void onComplete() {
                mRxOperatorsText.append("onComplete" + "\n");
                Log.e(TAG,"onComplete" + "\n");
            }
        });
    }

    private void map(){
        Observable.create(new ObservableOnSubscribe<Integer>() {
            @Override
            public void subscribe(ObservableEmitter<Integer> e) throws Exception {
                mRxOperatorsText.append("Observable emit 1\n");
                Log.e(TAG, "Observable emit 1\n");
                e.onNext(1);
                mRxOperatorsText.append("Observable emit 2\n");
                Log.e(TAG, "Observable emit 2\n");
                e.onNext(2);
                mRxOperatorsText.append("Observable emit 3\n");
                Log.e(TAG, "Observable emit 3\n");
                e.onNext(3);
            }
        }).map(new Function<Integer, String>() {
            @Override
            public String apply(Integer integer) throws Exception {
                mRxOperatorsText.append("apply : " + integer + "\n");
                Log.e(TAG, "apply : " + integer + "\n");
                return "This is result " + integer;
            }
        }).subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                mRxOperatorsText.append("accept : " + s + "\n");
                Log.e(TAG, "accept : " + s + "\n");
            }
        });
    }

    private void zip(){
        Observable.zip(getStringObservable(),getIntegerObservable(),)
    }

    private Observable<String> getStringObservable(){
        return Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                if(!e.isDisposed()){
                    e.onNext("A");
                }
            }
        });
    }
}
