package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthenticationError implements SecurityError {

    // ================= CREDENTIALS =================

    CREDENTIALS_INVALID(
            ErrorDomain.SECURITY,
            "SECURITY_CREDENTIALS_INVALID",
            HttpStatus.UNAUTHORIZED,
            "Invalid username or password"
    ),

    AUTHENTICATION_FAILED(
            ErrorDomain.SECURITY,
            "SECURITY_AUTHENTICATION_FAILED",
            HttpStatus.UNAUTHORIZED,
            "Authentication failed"
    ),

    AUTHENTICATION_UNSUPPORTED_MECHANISM(
            ErrorDomain.SECURITY,
            "SECURITY_AUTHENTICATION_UNSUPPORTED_MECHANISM",
            HttpStatus.UNAUTHORIZED,
            "Unsupported authentication mechanism"
    ),

    // ================= PRINCIPAL =================

    PRINCIPAL_NOT_FOUND(
            ErrorDomain.SECURITY,
            "SECURITY_PRINCIPAL_NOT_FOUND",
            HttpStatus.UNAUTHORIZED,
            "Principal not found"
    ),

    // ================= TOKEN =================

    TOKEN_INVALID(
            ErrorDomain.SECURITY,
            "SECURITY_TOKEN_INVALID",
            HttpStatus.UNAUTHORIZED,
            "Invalid token"
    ),

    TOKEN_EXPIRED(
            ErrorDomain.SECURITY,
            "SECURITY_TOKEN_EXPIRED",
            HttpStatus.UNAUTHORIZED,
            "Token expired"
    ),

    TOKEN_MALFORMED(
            ErrorDomain.SECURITY,
            "SECURITY_TOKEN_MALFORMED",
            HttpStatus.UNAUTHORIZED,
            "Malformed token"
    ),

    TOKEN_SIGNATURE_INVALID(
            ErrorDomain.SECURITY,
            "SECURITY_TOKEN_SIGNATURE_INVALID",
            HttpStatus.UNAUTHORIZED,
            "Invalid token signature"
    ),

    TOKEN_MISSING(
            ErrorDomain.SECURITY,
            "SECURITY_TOKEN_MISSING",
            HttpStatus.UNAUTHORIZED,
            "Authentication token is missing"
    );

    private final ErrorDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;
}