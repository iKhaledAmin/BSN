package com.khaled_amin.book_social_network.identity.capability.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum CapabilityApplicationErrorCode implements BaseError {

    NOT_FOUND("CAPABILITY_NOT_FOUND", HttpStatus.NOT_FOUND, "Capability not found"),
    ALREADY_EXISTS("CAPABILITY_ALREADY_EXISTS", HttpStatus.CONFLICT, "Capability already exists");

    private final String code;
    private final HttpStatus status;
    private final String message;

    CapabilityApplicationErrorCode(String code, HttpStatus status, String message) {
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
