package com.khaled_amin.book_social_network.core.policy.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum PolicyError implements BaseError {


    INVALID_POLICY_CONTEXT(
            "POLICY-INVALID_CONTEXT",
            HttpStatus.BAD_REQUEST,
            "Invalid policy context"
    );

    private final String code;
    private final HttpStatus status;
    private final String message;

    PolicyError(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
