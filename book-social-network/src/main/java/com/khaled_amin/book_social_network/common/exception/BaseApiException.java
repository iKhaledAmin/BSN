package com.khaled_amin.book_social_network.common.exception;

import com.khaled_amin.book_social_network.common.error.ApiErrorCode;

public abstract class BaseApiException extends RuntimeException {

    private final ApiErrorCode errorCode;

    protected BaseApiException(ApiErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public ApiErrorCode getErrorCode() {
        return errorCode;
    }
}