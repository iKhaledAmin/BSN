package com.khaled_amin.book_social_network.identity.core.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum IdentityError implements BaseError {
    IDENTITY_NOT_FOUND("IDENTITY_NOT_FOUND", HttpStatus.NOT_FOUND, "Identity not found"),
    INVALID_IDENTITY("IDENTITY_INVALID", HttpStatus.BAD_REQUEST, "Invalid identity");

    private final String code;
    private final HttpStatus status;
    private final String message;

    IdentityError(String code, HttpStatus status, String message) {
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
