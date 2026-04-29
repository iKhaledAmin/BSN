package com.khaled_amin.book_social_network.identity.verification.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum VerificationDomainError implements BaseError {

    INVALID_TOKEN("VERIFICATION_INVALID_TOKEN_CODE", HttpStatus.BAD_REQUEST, "Invalid token"),
    TOKEN_EXPIRED("VERIFICATION_TOKEN_EXPIRED", HttpStatus.BAD_REQUEST, "Token expired"),
    TOKEN_ALREADY_USED("VERIFICATION_TOKEN_ALREADY_USED", HttpStatus.CONFLICT, "Token already used"),
    INVALID_STATE("VERIFICATION_INVALID_STATE", HttpStatus.BAD_REQUEST, "Invalid verification state");

    private final String code;
    private final HttpStatus status;
    private final String message;

    VerificationDomainError(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override public String getCode() { return code; }
    @Override public HttpStatus getStatus() { return status; }
    @Override public String getMessage() { return message; }
}