package com.testapp.azurpoc.azurpoctestapp.remote.event;
/**
 * Created by Ivan Danilov on 4/3/2018.
 * Email - esseckers@gmail.com
 */
public interface IDataCallback<DATA> {
    void onStart();

    void onResponse(DATA data);

    void onFail(Throwable throwable);
}
