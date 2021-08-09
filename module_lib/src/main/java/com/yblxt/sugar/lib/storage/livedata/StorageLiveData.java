package com.yblxt.sugar.lib.storage.livedata;

import androidx.lifecycle.LiveData;

import com.yblxt.sugar.lib.storage.log.Logger;

/**
 * @author : evanyu
 * @date : 2021/6/23
 */
public class StorageLiveData<T> extends LiveData<T> {

    private final String mKey;
    private Class<T> mValueType;
    private final Callback mCallback;

    StorageLiveData(String key, T value, Class<T> valueType, Callback callback) {
        super();
        this.mKey = key;
        this.mValueType = valueType;
        this.mCallback = callback;
        postValue(value);
    }

    public Class<T> getValueType() {
        return mValueType;
    }

    @Override
    protected void postValue(T value) {
        super.postValue(value);
    }

    @Override
    protected void onActive() {
        Logger.d("StorageLiveData -> onActive, key=%s", mKey);
        super.onActive();
    }

    @Override
    protected void onInactive() {
        Logger.d("StorageLiveData -> onInactive, key=%s", mKey);
        super.onInactive();
        if (mCallback != null) {
            mCallback.onInactive(mKey);
        }
    }

    interface Callback {
        void onInactive(String key);
    }
}
