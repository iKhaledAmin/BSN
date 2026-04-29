package com.khaled_amin.book_social_network.identity.verification.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import com.khaled_amin.book_social_network.core.exception.BaseException;

public class VerificationApplicationException extends BaseException {

    private VerificationApplicationException(BaseError error, String message) {
        super(error, message);
    }

    public static VerificationApplicationException tokenNotFound() {
        return new VerificationApplicationException(
                VerificationApplicationError.TOKEN_NOT_FOUND,
                "Verification token not found"
        );
    }
}