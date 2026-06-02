package com.khaled_amin.book_social_network.identity.verification.domain.exception;

import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VerificationDomainError implements BusinessError {

    TOKEN_CODE_INVALID(
            ErrorDomain.IDENTITY,
            "VERIFICATION_TOKEN_CODE_INVALID",
            HttpStatus.BAD_REQUEST,
            "Invalid token"
    ),

    TOKEN_EXPIRED(
            ErrorDomain.IDENTITY,
            "VERIFICATION_TOKEN_EXPIRED",
            HttpStatus.BAD_REQUEST,
            "Token expired"
    ),

    TOKEN_ALREADY_USED(
            ErrorDomain.IDENTITY,
            "VERIFICATION_TOKEN_ALREADY_USED",
            HttpStatus.CONFLICT,
            "Token already used"
    ),


    INVALID_STATE(
            ErrorDomain.IDENTITY,
            "VERIFICATION_INVALID_STATE",
            HttpStatus.BAD_REQUEST,
            "Invalid verification state"
    );

    private final ErrorDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;

}