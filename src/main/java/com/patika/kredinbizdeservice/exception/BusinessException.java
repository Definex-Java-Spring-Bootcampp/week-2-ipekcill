package com.patika.kredinbizdeservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException {
    private Object[] params;

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Object[] params) {
        super(message);
        this.params = params;
    }

    public BusinessException(String message, Throwable cause, Object[] params) {
        super(message, cause);
        this.params = params;
    }

    public BusinessException(Throwable cause, Object[] params) {
        super(cause);
        this.params = params;
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, Object[] params) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.params = params;
    }
}
