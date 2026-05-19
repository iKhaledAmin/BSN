package com.khaled_amin.book_social_network.identity.capability.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum CapabilityDomainErrorCode implements BaseError {

    INVALID_CAPABILITY("CAPABILITY_INVALID_CAPABILITY", HttpStatus.BAD_REQUEST, "Capability is invalid"),
    INVALID_COMMAND("CAPABILITY_INVALID_COMMAND", HttpStatus.BAD_REQUEST, "Capability command is invalid");

    private final String code;
    private final HttpStatus status;
    private final String message;

    CapabilityDomainErrorCode(String code, HttpStatus status, String message) {
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
