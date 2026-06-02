package com.khaled_amin.book_social_network.identity.core.exception;

import com.khaled_amin.book_social_network.core.exception.validation.ValidationError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum IdentityValidationError implements ValidationError {

    ACTOR_CODE_INVALID(
            ErrorDomain.IDENTITY,
            "IDENTITY_ACTOR_CODE_INVALID",
            "Invalid actor code"
    ),
    ACTOR_TYPE_INVALID(
            ErrorDomain.IDENTITY,
            "IDENTITY_ACTOR_TYPE_INVALID",
            "Invalid actor type"
    )

    ;
    private final ErrorDomain domain;
    private final String code;
    private final String message;
}
