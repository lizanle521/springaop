package com.lzl.design_pattern.K_Composite;

/**
 * @author lizanle
 * @data 2019/3/30 1:59 PM
 */
public class FileNoAddMethodException extends Exception {
    public FileNoAddMethodException() {
        super();
    }

    public FileNoAddMethodException(String message) {
        super(message);
    }

    public FileNoAddMethodException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNoAddMethodException(Throwable cause) {
        super(cause);
    }

    protected FileNoAddMethodException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
