package com.khaled_amin.book_social_network.identity.core.exception;


import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum IdentityTechnicalError implements TechnicalError {
    INVALID(
            SystemDomain.IDENTITY,
            "IDENTITY_INVALID",
            "Invalid identity"
    ),

    GENERATED_ACTOR_CODE_INVALID(
            SystemDomain.IDENTITY,
            "IDENTITY_GENERATED_ACTOR_CODE_INVALID",
            "Failed to generate valid actor code"
    ),


    ACTOR_SOURCE_TYPE_MISMATCH(
            SystemDomain.IDENTITY,
            "ACTOR_SOURCE_TYPE_MISMATCH",
                    "Actor source type mismatch"
    ),

    ACTOR_PRINCIPAL_TYPE_MISMATCH(
            SystemDomain.IDENTITY,
            "ACTOR_PRINCIPAL_TYPE_MISMATCH",
                    "Principal type mismatch"
    ),

    ACTOR_RESOLVER_NOT_FOUND(
            SystemDomain.IDENTITY,
            "ACTOR_RESOLVER_NOT_FOUND",
                    "No resolver registered for actor type"
    ),

    ACTOR_REGISTRY_CONFLICT(
            SystemDomain.IDENTITY,
            "ACTOR_REGISTRY_CONFLICT",
                    "Duplicate resolver registration detected"
    ),

    ;

    private final SystemDomain domain;
    private final String code;
    private final String message;

}
