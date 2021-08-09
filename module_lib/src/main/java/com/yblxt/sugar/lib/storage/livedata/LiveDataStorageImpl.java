package com.yblxt.sugar.lib.storage.livedata;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;

import com.yblxt.sugar.lib.storage.Storage;
import com.yblxt.sugar.lib.storage.log.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : evanyu
 * @date : 2021/6/23
 */
@SuppressWarnings("unchecked")
public class LiveDataStorageImpl implements LiveDataStorage {

    @NonNull
    private final Storage mStorage;

    private final Map<String, StorageLiveData<?>> mLiveDataMap = new ConcurrentHashMap<>();

    private final StorageLiveData.Callback mLiveDataCallback = key -> {
        Logger.d("LiveDataStorage -> onLiveDataInactive, key=%s", key);
        mLiveDataMap.remove(key);
    };

    public LiveDataStorageImpl(@NonNull Storage storage) {
        this.mStorage = storage;
    }

    @Override
    public LiveData<Boolean> getBoolean(String key) {
        return getBoolean(key, Storage.DEF_BOOL);
    }

    @Override
    public LiveData<Boolean> getBoolean(String key, boolean defVal) {
        return getLiveData(key, defVal, Boolean.class);
    }

    @Override
    public LiveData<Integer> getInt(String key) {
        return getInt(key, Storage.DEF_INT);
    }

    @Override
    public LiveData<Integer> getInt(String key, int defVal) {
        return getLiveData(key, defVal, Integer.class);
    }

    @Override
    public LiveData<Long> getLong(String key) {
        return getLong(key, Storage.DEF_LONG);
    }

    @Override
    public LiveData<Long> getLong(String key, long defVal) {
        return getLiveData(key, defVal, Long.class);
    }

    @Override
    public LiveData<Float> getFloat(String key) {
        return getFloat(key, Storage.DEF_FLOAT);
    }

    @Override
    public LiveData<Float> getFloat(String key, float defVal) {
        return getLiveData(key, defVal, Float.class);
    }

    @Override
    public LiveData<Double> getDouble(String key) {
        return getDouble(key, Storage.DEF_DOUBLE);
    }

    @Override
    public LiveData<Double> getDouble(String key, double defVal) {
        return getLiveData(key, defVal, Double.class);
    }

    @Override
    public LiveData<String> getString(String key) {
        return getString(key, Storage.DEF_STR);
    }

    @Override
    public LiveData<String> getString(String key, String defVal) {
        return getLiveData(key, defVal, String.class);
    }

    private <T> LiveData<T> getLiveData(String key, T defVal, Class<T> valueType) {
        String targetTypeName = valueType.getSimpleName();
        Logger.i("LiveDataStorage -> get%s, key=%s", targetTypeName, key);
        StorageLiveData<?> liveData = mLiveDataMap.computeIfAbsent(key, k -> {
            T value = mStorage.get(key, defVal, valueType);
            return new StorageLiveData<>(key, value, valueType, mLiveDataCallback);
        });
        if (liveData.getValueType() == valueType) {
            return (LiveData<T>) liveData;
        } else {
            Logger.w("LiveDataStorage -> getLiveData failed: value type error, key=%s, existsType=%s, targetType=%s",
                    key, liveData.getValueType().getSimpleName(), targetTypeName);
            // empty LiveData instance
            return new LiveData<T>() { };
        }
    }

    public <T> void onSaveKeyValue(String key, T value, Class<T> valueType) {
        try {
            String targetTypeName = valueType.getSimpleName();
            if (!mLiveDataMap.containsKey(key)) {
                return;
            }
            StorageLiveData<?> liveData = mLiveDataMap.get(key);
            if (liveData == null) {
                mLiveDataMap.remove(key);
                return;
            }
            if (value == null || value.equals(liveData.getValue())) {
                Logger.d("LiveDataStorage # onSaveValue -> value is null or unchanged");
                return;
            }
            if (liveData.getValueType() != valueType) {
                Logger.w("LiveDataStorage # onSaveValue -> value type error, key=%s, existsType=%s, targetType=%s",
                        liveData.getValueType().getSimpleName(), targetTypeName);
                return;
            }
            ((StorageLiveData<T>) liveData).postValue(value);
        } catch (Exception e) {
            Logger.w("LiveDataStorage # onSaveValue -> an exception occurred: %s", Log.getStackTraceString(e));
        }
    }

}
