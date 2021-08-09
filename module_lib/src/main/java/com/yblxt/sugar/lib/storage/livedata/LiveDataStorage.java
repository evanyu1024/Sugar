package com.yblxt.sugar.lib.storage.livedata;

import androidx.lifecycle.LiveData;

/**
 * @author : evanyu
 * @date : 2021/6/23
 */
public interface LiveDataStorage {

    LiveData<Boolean> getBoolean(String key);

    LiveData<Boolean> getBoolean(String key, boolean defVal);

    LiveData<Integer> getInt(String key);

    LiveData<Integer> getInt(String key, int defVal);

    LiveData<Long> getLong(String key);

    LiveData<Long> getLong(String key, long defVal);

    LiveData<Float> getFloat(String key);

    LiveData<Float> getFloat(String key, float defVal);

    LiveData<Double> getDouble(String key);

    LiveData<Double> getDouble(String key, double defVal);

    LiveData<String> getString(String key);

    LiveData<String> getString(String key, String defVal);

}
