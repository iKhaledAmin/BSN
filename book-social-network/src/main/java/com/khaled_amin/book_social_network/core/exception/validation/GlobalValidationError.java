package com.khaled_amin.book_social_network.core.exception.validation;

import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;


@Getter
@RequiredArgsConstructor
public enum GlobalValidationError implements ValidationError{

    VALIDATION_ERROR(
            ErrorDomain.CORE,
            "CORE_EXCEPTION_VALIDATION",
            "Validation failed"
    ),

    ;
    private final ErrorDomain domain;
    private final String code;
    private final String message;
}
