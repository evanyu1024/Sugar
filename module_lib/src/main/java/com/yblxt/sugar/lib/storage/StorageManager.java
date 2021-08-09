package com.yblxt.sugar.lib.storage;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;

import com.yblxt.sugar.lib.storage.disk.DiskStorage;
import com.yblxt.sugar.lib.storage.log.Logger;
import com.yblxt.sugar.lib.storage.memory.MemoryStorage;

import timber.log.Timber;

/**
 * @author : evanyu
 * @date : 2021/6/21
 */
public class StorageManager {

    private static String sTag = "StorageSDK";

    private static boolean sInitFlag;

    public static void init(@NonNull Context context) {
        if (!(context instanceof Application)) {
            context = context.getApplicationContext();
        }

        sTag = "StorageSDK-[" + context.getPackageName() + "]";
        Logger.i("StorageSDK init start");
        if (sInitFlag) {
            Timber.tag(sTag).w("StorageSDK init failed: library has been initialized.");
            return;
        }
        DiskStorage.init(context);
        sInitFlag = true;
        Logger.i("StorageSDK init end");
    }

    public static String getTag() {
        return sTag;
    }

    /**
     * Get default instance of {@link DiskStorage}.
     */
    public static Storage disk() {
        checkInitState("disk");
        return DiskStorage.getDefault();
    }

    public static Storage memory() {
        checkInitState("memory");
        return MemoryStorage.getDefault();
    }

    private static void checkInitState(String method) {
        if (!sInitFlag) {
            Logger.w("StorageSDK has not been initialized, " +
                    "please call the StorageManager#init(ctx) method to initialize the SDK first. (%s)", method);
        }
    }

}