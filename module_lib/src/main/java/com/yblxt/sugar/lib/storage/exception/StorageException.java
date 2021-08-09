package com.yblxt.sugar.lib.storage.exception;

/**
 * @author : evanyu
 * @date : 2021/6/24
 */
public class StorageException extends RuntimeException {

    public StorageException(String format, Object... args) {
        super(String.format(format, args));
    }

}
