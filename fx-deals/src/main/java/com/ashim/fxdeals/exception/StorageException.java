package com.ashim.fxdeals.exception;

/**
 * @author ashimjk on 12/1/2018
 */
public class StorageException extends RuntimeException {

    public StorageException(String message) {
        super(message);
    }

    public StorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
