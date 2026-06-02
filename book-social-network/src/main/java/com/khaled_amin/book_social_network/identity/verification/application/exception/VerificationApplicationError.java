package com.khaled_amin.book_social_network.identity.verification.application.exception;

import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum VerificationApplicationError implements BusinessError {

    TOKEN_NOT_FOUND(
            ErrorDomain.IDENTITY,
            "VERIFICATION_TOKEN_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Verification token not found"
    )


    ;

    private final ErrorDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;
}
