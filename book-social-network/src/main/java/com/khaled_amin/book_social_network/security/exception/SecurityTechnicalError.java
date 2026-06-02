package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SecurityTechnicalError implements TechnicalError {

    // ================================= PRINCIPAL RESOLVER =================================

    PRINCIPAL_RESOLVER_DUPLICATE(
            ErrorDomain.SECURITY,
            "SECURITY_PRINCIPAL_RESOLVER_DUPLICATE",
            "Duplicate PrincipalResolver registered"
    ),

    PRINCIPAL_RESOLVER_NULL(
            ErrorDomain.SECURITY,
            "SECURITY_PRINCIPAL_RESOLVER_NULL",
            "No PrincipalResolver registered"
    ),

    // ================================= AUTHENTICATED PRINCIPAL =================================

    UNSUPPORTED_PRINCIPAL_TYPE(
            ErrorDomain.SECURITY,
            "SECURITY_UNSUPPORTED_PRINCIPAL_TYPE",
            "Unsupported authenticated principal type"
    ),

    // ================================= JWT CLAIMS CONTRIBUTOR =================================

    JWT_CLAIMS_CONTRIBUTOR_DUPLICATE(
            ErrorDomain.SECURITY,
            "SECURITY_JWT_CLAIMS_CONTRIBUTOR_DUPLICATE",
            "Duplicate JwtClaimsContributor registered"
    ),

    JWT_CLAIMS_CONTRIBUTOR_NULL(
            ErrorDomain.SECURITY,
            "SECURITY_JWT_CLAIMS_CONTRIBUTOR_NULL",
            "No JwtClaimsContributor registered"
    ),

    // ================================= CONFIGURATION =================================

    CONFIGURATION_INVALID(
            ErrorDomain.SECURITY,
            "SECURITY_CONFIGURATION_INVALID",
            "Invalid security configuration"
    );

    private final ErrorDomain domain;
    private final String code;
    private final String message;
}