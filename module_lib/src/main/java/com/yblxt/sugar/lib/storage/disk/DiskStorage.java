package com.yblxt.sugar.lib.storage.disk;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.tencent.mmkv.MMKV;
import com.yblxt.sugar.lib.storage.Storage;
import com.yblxt.sugar.lib.storage.exception.StorageException;
import com.yblxt.sugar.lib.storage.livedata.LiveDataStorageImpl;
import com.yblxt.sugar.lib.storage.log.Logger;

/**
 * @author : evanyu
 * @date : 2021/6/21
 */
@SuppressWarnings({"unchecked"})
public class DiskStorage implements Storage {

    private final MMKV mmkv;
    private final LiveDataStorageImpl mLiveDataStorage;

    public static void init(@NonNull Context context) {
        MMKV.initialize(context);
    }

    private DiskStorage(String fileName) {
        mmkv = MMKV.mmkvWithID(fileName);
        mLiveDataStorage = new LiveDataStorageImpl(this);
    }

    private static class DefaultHolder {
        static final DiskStorage INSTANCE = new DiskStorage("default");
    }

    public static DiskStorage getDefault() {
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
        Logger.i("DiskStorage # get%s -> key=%s, defVal=%s", targetTypeName, key, defVal);
        if (key == null) {
            Logger.w("DiskStorage -> get failed: key cannot be null");
            return defVal;
        }
        T value = defVal;
        try {
            if (valueType == Boolean.class) {
                value = (T) (Boolean) mmkv.decodeBool(key, (boolean) defVal);
            } else if (valueType == Integer.class) {
                value = (T) (Integer) mmkv.decodeInt(key, (int) defVal);
            } else if (valueType == Long.class) {
                value = (T) (Long) mmkv.decodeLong(key, (long) defVal);
            } else if (valueType == Float.class) {
                value = (T) (Float) mmkv.decodeFloat(key, (float) defVal);
            } else if (valueType == Double.class) {
                value = (T) (Double) mmkv.decodeDouble(key, (double) defVal);
            } else if (valueType == String.class) {
                value = (T) mmkv.decodeString(key, (String) defVal);
            } else {
                throw new StorageException("unknown type: %s", valueType);
            }
        } catch (Exception e) {
            Logger.w("DiskStorage -> get failed, cause: %s", Log.getStackTraceString(e));
        }
        Logger.i("DiskStorage # get%s -> %s=%s", targetTypeName, key, value);
        return value;
    }

    private <T> boolean save(String key, T value, @NonNull Class<T> valueType) {
        String targetTypeName = valueType.getSimpleName();
        Logger.i("DiskStorage # save%s -> %s=%s", targetTypeName, key, value);
        if (key == null) {
            Logger.w("DiskStorage -> save failed: key cannot be null");
            return false;
        }
        if (value == null) {
            Logger.w("DiskStorage -> save failed: value cannot be null");
            return false;
        }
        boolean isSuccessful = false;
        try {
            if (valueType == Boolean.class) {
                isSuccessful = mmkv.encode(key, (boolean) value);
            } else if (valueType == Integer.class) {
                isSuccessful = mmkv.encode(key, (int) value);
            } else if (valueType == Long.class) {
                isSuccessful = mmkv.encode(key, (long) value);
            } else if (valueType == Float.class) {
                isSuccessful = mmkv.encode(key, (float) value);
            } else if (valueType == Double.class) {
                isSuccessful = mmkv.encode(key, (double) value);
            } else if (valueType == String.class) {
                isSuccessful = mmkv.encode(key, (String) value);
            } else {
                throw new StorageException("unknown type: %s", valueType);
            }
            if (!isSuccessful) {
                Logger.w("DiskStorage -> save failed, cause: mmkv.encode(%s,%s)=false", key, value);
            }
        } catch (Exception e) {
            Logger.w("DiskStorage -> save failed, cause: %s", Log.getStackTraceString(e));
        }
        if (isSuccessful) {
            mLiveDataStorage.onSaveKeyValue(key, value, valueType);
        }
        return isSuccessful;
    }

}
