package com.khaled_amin.book_social_network.core.exception.technical;

import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GlobalTechnicalError implements TechnicalError{

    INTERNAL_SERVER_ERROR(
            ErrorDomain.CORE,
            "CORE_EXCEPTION_INTERNAL_ERROR",
            "Unexpected internal server error"
    ),

    ;

    private final ErrorDomain domain;
    private final String code;
    private final String message;
}
