package com.testapp.azurpoc.azurpoctestapp.utils;


import com.testapp.azurpoc.azurpoctestapp.remote.event.IDataCallback;

import java.util.concurrent.Callable;

import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public class RxUtils {

    /**
     * Create from {@link Observable} from function
     *
     * @param func Callable<T>
     */
    public static <DATA> Single<DATA> makeSingle(Callable<DATA> func) {
        return Single.fromCallable(func);
    }

    /**
     * Method for starting to perform asynchronous tasks
     *
     * @param single          @see {@link Single}
     * @param callback            @see {@link IDataCallback}
     */
    public static <DATA> Disposable executeSingle(Single<DATA> single,
                                                  IDataCallback<DATA> callback) {
        return single.compose(applySchedulers())
                .doOnSubscribe(disposable -> callback.onStart())
                .subscribe(callback::onResponse, callback::onFail);
    }

    private static <T> SingleTransformer<T, T> applySchedulers() {
        return upstream -> upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}
