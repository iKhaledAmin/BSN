package com.khaled_amin.book_social_network.identity.core.exception;

import com.khaled_amin.book_social_network.core.exception.TechnicalError;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ActorResolutionError implements TechnicalError {

    ACTOR_SOURCE_TYPE_MISMATCH(
            "ACTOR_SOURCE_TYPE_MISMATCH",
            "Actor source type mismatch"
    ),

    ACTOR_PRINCIPAL_TYPE_MISMATCH(
            "ACTOR_PRINCIPAL_TYPE_MISMATCH",
            "Principal type mismatch"
    ),

    ACTOR_RESOLVER_NOT_FOUND(
            "ACTOR_RESOLVER_NOT_FOUND",
            "No resolver registered for actor type"
    ),

    ACTOR_REGISTRY_CONFLICT(
            "ACTOR_REGISTRY_CONFLICT",
            "Duplicate resolver registration detected"
    ),

    ACTOR_UNSUPPORTED_TYPE(
            "ACTOR_UNSUPPORTED_TYPE",
            "Unsupported actor type"
    ),

    ACTOR_INVALID_TYPE(
            "ACTOR_INVALID_TYPE",
            "Invalid actor type"
    );

    private final String code;
    private final String message;
}