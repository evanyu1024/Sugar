package com.yblxt.sugar.lib.storage.log;

import com.yblxt.sugar.lib.storage.StorageManager;

import timber.log.Timber;

/**
 * @author : evanyu
 * @date : 2021/6/23
 */
public class Logger {

    public static void d(String format, Object... args) {
        Timber.tag(StorageManager.getTag()).d(format, args);
    }

    public static void i(String format, Object... args) {
        Timber.tag(StorageManager.getTag()).i(format, args);
    }

    public static void w(String format, Object... args) {
        Timber.tag(StorageManager.getTag()).w(format, args);
    }

}
