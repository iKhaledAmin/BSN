package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.TechnicalError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum PrincipalResolutionError implements TechnicalError {

    PRINCIPAL_DUPLICATE_RESOLVER(
            "PRINCIPAL_DUPLICATE_RESOLVER",
            "Duplicate PrincipalResolver registered"
    ),

    PRINCIPAL_MISSING_RESOLVER(
            "PRINCIPAL_MISSING_RESOLVER",
            "No PrincipalResolver registered"
    );

    private final String code;
    private final String message;
}