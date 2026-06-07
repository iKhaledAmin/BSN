package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthorizationError implements SecurityError {

    ACCESS_DENIED(
            SystemDomain.SECURITY,
            "SECURITY_ACCESS_DENIED",
            HttpStatus.FORBIDDEN,
            "Access denied"
    ),

    INSUFFICIENT_SCOPE(
            SystemDomain.SECURITY,
            "SECURITY_INSUFFICIENT_SCOPE",
            HttpStatus.FORBIDDEN,
            "Insufficient permissions"
    ),


    ;

    private final SystemDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;
}