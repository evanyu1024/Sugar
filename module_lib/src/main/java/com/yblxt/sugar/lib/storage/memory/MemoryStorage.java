package com.yblxt.sugar.lib.storage.memory;

import com.yblxt.sugar.lib.storage.Storage;
import com.yblxt.sugar.lib.storage.livedata.LiveDataStorageImpl;
import com.yblxt.sugar.lib.storage.log.Logger;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author : evanyu
 * @date : 2021/6/24
 */
@SuppressWarnings({"unchecked", "ConstantConditions"})
public class MemoryStorage implements Storage {

    private final Map<String, Object> mData;
    private final LiveDataStorageImpl mLiveDataStorage;

    private MemoryStorage() {
        mData = new ConcurrentHashMap<>();
        mLiveDataStorage = new LiveDataStorageImpl(this);
    }

    private static class DefaultHolder {
        static final MemoryStorage INSTANCE = new MemoryStorage();
    }

    public static MemoryStorage getDefault() {
        return DefaultHolder.INSTANCE;
    }

    @Override
    public LiveDataStorageImpl withLiveData() {
        return mLiveDataStorage;
    }

    @Override
    public boolean save(String key, boolean value) {
        return save(key, value, Boolean.class);
    }

    @Override
    public boolean getBoolean(String key) {
        return getBoolean(key, DEF_BOOL);
    }

    @Override
    public boolean getBoolean(String key, boolean defVal) {
        return get(key, defVal, Boolean.class);
    }

    @Override
    public boolean save(String key, int value) {
        return save(key, value, Integer.class);
    }

    @Override
    public int getInt(String key) {
        return getInt(key, DEF_INT);
    }

    @Override
    public int getInt(String key, int defVal) {
        return get(key, defVal, Integer.class);
    }

    @Override
    public boolean save(String key, long value) {
        return save(key, value, Long.class);
    }

    @Override
    public long getLong(String key) {
        return getLong(key, DEF_LONG);
    }

    @Override
    public long getLong(String key, long defVal) {
        return get(key, defVal, Long.class);
    }

    @Override
    public boolean save(String key, float value) {
        return save(key, value, Float.class);
    }

    @Override
    public float getFloat(String key) {
        return getFloat(key, DEF_FLOAT);
    }

    @Override
    public float getFloat(String key, float defVal) {
        return get(key, defVal, Float.class);
    }

    @Override
    public boolean save(String key, double value) {
        return save(key, value, Double.class);
    }

    @Override
    public double getDouble(String key) {
        return getDouble(key, DEF_DOUBLE);
    }

    @Override
    public double getDouble(String key, double defVal) {
        return get(key, defVal, Double.class);
    }

    @Override
    public boolean save(String key, String value) {
        return save(key, value, String.class);
    }

    @Override
    public String getString(String key) {
        return getString(key, DEF_STR);
    }

    @Override
    public String getString(String key, String defVal) {
        return get(key, defVal, String.class);
    }

    @Override
    public <T> T get(String key, T defVal, Class<T> valueType) {
        String targetTypeName = valueType.getSimpleName();
        Logger.i("MemoryStorage # get%s -> key=%s, defVal=%s", targetTypeName, key, defVal);
        if (key == null) {
            Logger.w("MemoryStorage -> get failed: key cannot be null");
            return defVal;
        }
        Object value = mData.get(key);
        if (value != null && !valueType.isInstance(value)) {
            Logger.w("MemoryStorage -> get failed: value type error, key=%s, existsType=%s, targetType=%s",
                    key, value.getClass().getSimpleName(), targetTypeName);
            return defVal;
        }
        Logger.d("MemoryStorage # get%s -> %s=%s", targetTypeName, key, value);
        return (T) value;
    }

    private <T> boolean save(String key, T value, Class<T> valueType) {
        String targetTypeName = valueType.getSimpleName();
        Logger.i("MemoryStorage # save%s -> %s=%s", targetTypeName, key, value);
        if (key == null) {
            Logger.w("MemoryStorage -> save failed: key cannot be null");
            return false;
        }
        if (value == null) {
            Logger.w("MemoryStorage -> save failed: value cannot be null");
            return false;
        }
        Object oldVal = mData.get(key);
        if (oldVal != null && !valueType.isInstance(oldVal)) {
            Logger.w("MemoryStorage -> save failed: value type error, key=%s, existsType=%s, targetType=%s",
                    key, oldVal.getClass().getSimpleName(), targetTypeName);
            return false;
        }
        mData.put(key, value);
        mLiveDataStorage.onSaveKeyValue(key, value, valueType);
        return true;
    }

}
