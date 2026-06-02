package com.khaled_amin.book_social_network.identity.verification.application.exception;

import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.business.BusinessException;

public class VerificationApplicationException extends BusinessException {

    private VerificationApplicationException(BusinessError error, String message) {
        super(error, message);
    }

    public static VerificationApplicationException tokenNotFound() {
        return new VerificationApplicationException(
                VerificationApplicationError.TOKEN_NOT_FOUND,
                "Verification token not found"
        );
    }
}