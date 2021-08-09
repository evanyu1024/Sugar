package com.yblxt.sugar.lib.storage;

import com.yblxt.sugar.lib.storage.livedata.LiveDataStorageImpl;

/**
 * @author : evanyu
 * @date : 2021/6/21
 */
public interface Storage {

    boolean DEF_BOOL = false;
    int DEF_INT = -1;
    long DEF_LONG = -1L;
    float DEF_FLOAT = -1.0F;
    double DEF_DOUBLE = -1.0;
    String DEF_STR = null;

    LiveDataStorageImpl withLiveData();

    boolean save(String key, boolean value);

    boolean getBoolean(String key);

    boolean getBoolean(String key, boolean defVal);

    boolean save(String key, int value);

    int getInt(String key);

    int getInt(String key, int defVal);

    boolean save(String key, long value);

    long getLong(String key);

    long getLong(String key, long defVal);

    boolean save(String key, float value);

    float getFloat(String key);

    float getFloat(String key, float defVal);

    boolean save(String key, double value);

    double getDouble(String key);

    double getDouble(String key, double defVal);

    boolean save(String key, String value);

    String getString(String key);

    String getString(String key, String defVal);

    <T> T get(String key, T defVal, Class<T> valueType);

}
