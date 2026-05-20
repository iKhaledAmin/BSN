package com.khaled_amin.book_social_network.identity.verification.application.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import org.springframework.http.HttpStatus;

public enum VerificationApplicationError implements BusinessError {

    TOKEN_NOT_FOUND("VERIFICATION_TOKEN_NOT_FOUND", HttpStatus.NOT_FOUND, "Verification token not found")
    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    VerificationApplicationError(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override public String getCode() { return code; }
    @Override public HttpStatus getStatus() { return status; }
    @Override public String getMessage() { return message; }
}
