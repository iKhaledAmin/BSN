package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthorizationError implements SecurityError {

    ACCESS_DENIED(
            ErrorDomain.SECURITY,
            "SECURITY_ACCESS_DENIED",
            HttpStatus.FORBIDDEN,
            "Access denied"
    ),

    INSUFFICIENT_SCOPE(
            ErrorDomain.SECURITY,
            "SECURITY_INSUFFICIENT_SCOPE",
            HttpStatus.FORBIDDEN,
            "Insufficient permissions"
    ),

    PRINCIPAL_LOCKED(
            ErrorDomain.SECURITY,
            "SECURITY_PRINCIPAL_LOCKED",
            HttpStatus.FORBIDDEN,
            "Principal is locked"
    ),

    PRINCIPAL_INACTIVE(
            ErrorDomain.SECURITY,
            "SECURITY_PRINCIPAL_INACTIVE",
            HttpStatus.FORBIDDEN,
            "Principal is inactive"
    );

    private final ErrorDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;
}