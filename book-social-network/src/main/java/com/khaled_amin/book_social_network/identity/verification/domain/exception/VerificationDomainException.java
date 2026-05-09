package com.khaled_amin.book_social_network.identity.verification.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;

public class VerificationDomainException extends BusinessException {

    private VerificationDomainException(BaseError error, String message) {
        super(error, message);
    }

    public static VerificationDomainException of(VerificationDomainError error) {
        return new VerificationDomainException(error, error.getMessage());
    }

    public static VerificationDomainException invalidToken() {
        return of(VerificationDomainError.INVALID_TOKEN);
    }

    public static VerificationDomainException expired() {
        return of(VerificationDomainError.TOKEN_EXPIRED);
    }

    public static VerificationDomainException alreadyUsed() {
        return of(VerificationDomainError.TOKEN_ALREADY_USED);
    }

    public static VerificationDomainException invalidState() {
        return of(VerificationDomainError.INVALID_STATE);
    }
}