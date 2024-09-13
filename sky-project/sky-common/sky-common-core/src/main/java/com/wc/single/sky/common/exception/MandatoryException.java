package com.wc.single.sky.common.exception;

/**
 *
 */
public class MandatoryException extends Exception {

    private Long code;

    private MandatoryException() {}

    public MandatoryException(String message) {
        super(message);
    }

    public MandatoryException(Long code, String message) {
        super(message);
        this.code = code;
    }

    public MandatoryException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public MandatoryException(Throwable throwable) {
        super(throwable);
    }

    public MandatoryException(Long code, String message, Throwable throwable) {
        super(message, throwable);
        this.code = code;
    }
}
