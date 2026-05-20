package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import org.springframework.http.HttpStatus;

public enum SecurityError implements BusinessError {

    // ================= AUTHENTICATION =================

    INVALID_CREDENTIALS("SECURITY_INVALID_CREDENTIALS", HttpStatus.UNAUTHORIZED, "Invalid username or password"),

    AUTHENTICATION_FAILED("SECURITY_AUTHENTICATION_FAILED", HttpStatus.UNAUTHORIZED, "Authentication failed"),

    UNSUPPORTED_AUTHENTICATION("SECURITY_UNSUPPORTED_AUTHENTICATION", HttpStatus.UNAUTHORIZED, "Unsupported authentication mechanism"),



    // ================= TOKEN =================

    TOKEN_INVALID("SECURITY_TOKEN_INVALID", HttpStatus.UNAUTHORIZED, "Invalid token"),

    TOKEN_EXPIRED("SECURITY_TOKEN_EXPIRED", HttpStatus.UNAUTHORIZED, "Token expired"),

    TOKEN_MALFORMED("SECURITY_TOKEN_MALFORMED", HttpStatus.UNAUTHORIZED, "Malformed token"),

    TOKEN_SIGNATURE_INVALID("SECURITY_TOKEN_SIGNATURE_INVALID", HttpStatus.UNAUTHORIZED, "Invalid token signature"),

    TOKEN_MISSING("SECURITY_TOKEN_MISSING", HttpStatus.UNAUTHORIZED, "Authentication token is missing"),




    // ================= AUTHORIZATION =================

    ACCESS_DENIED("SECURITY_ACCESS_DENIED", HttpStatus.FORBIDDEN, "Access denied"),

    INSUFFICIENT_SCOPE("SECURITY_INSUFFICIENT_SCOPE", HttpStatus.FORBIDDEN, "Insufficient permissions"),



    // ================= PRINCIPAL STATE =================

    PRINCIPAL_NOT_ACTIVE("SECURITY_PRINCIPAL_NOT_ACTIVE", HttpStatus.UNAUTHORIZED, "Principal is not active"),

    PRINCIPAL_LOCKED("SECURITY_PRINCIPAL_LOCKED", HttpStatus.UNAUTHORIZED, "Principal is locked")



    ;

    private final String code;
    private final HttpStatus status;
    private final String message;

    SecurityError(String code, HttpStatus status, String message) {
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