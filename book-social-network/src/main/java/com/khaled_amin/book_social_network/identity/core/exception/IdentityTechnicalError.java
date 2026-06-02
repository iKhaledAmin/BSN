package com.khaled_amin.book_social_network.identity.core.exception;


import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum IdentityTechnicalError implements TechnicalError {
    INVALID(
            ErrorDomain.IDENTITY,
            "IDENTITY_INVALID",
            "Invalid identity"
    ),

    GENERATED_ACTOR_CODE_INVALID(
            ErrorDomain.IDENTITY,
            "IDENTITY_GENERATED_ACTOR_CODE_INVALID",
            "Failed to generate valid actor code"
    ),


    ACTOR_SOURCE_TYPE_MISMATCH(
            ErrorDomain.IDENTITY,
            "ACTOR_SOURCE_TYPE_MISMATCH",
                    "Actor source type mismatch"
    ),

    ACTOR_PRINCIPAL_TYPE_MISMATCH(
            ErrorDomain.IDENTITY,
            "ACTOR_PRINCIPAL_TYPE_MISMATCH",
                    "Principal type mismatch"
    ),

    ACTOR_RESOLVER_NOT_FOUND(
            ErrorDomain.IDENTITY,
            "ACTOR_RESOLVER_NOT_FOUND",
                    "No resolver registered for actor type"
    ),

    ACTOR_REGISTRY_CONFLICT(
            ErrorDomain.IDENTITY,
            "ACTOR_REGISTRY_CONFLICT",
                    "Duplicate resolver registration detected"
    ),

    ;

    private final ErrorDomain domain;
    private final String code;
    private final String message;

}
