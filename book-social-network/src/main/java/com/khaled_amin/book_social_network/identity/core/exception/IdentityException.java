package com.khaled_amin.book_social_network.identity.core.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;

public class IdentityException extends BusinessException {

    protected IdentityException(BusinessError error, String message) {
        super(error, message);
    }

    // -------------------- Generic -------------------- //
    public static IdentityException of(IdentityError error) {
        return new IdentityException(error, error.getMessage());
    }

    public static IdentityException of(IdentityError error, String customMessage) {
        return new IdentityException(error, customMessage);
    }

    // -------------------- Common -------------------- //


    public static IdentityException notFound() {
        return of(IdentityError.IDENTITY_NOT_FOUND);
    }

    public static IdentityException invalidIdentity() {
        return of(IdentityError.INVALID_IDENTITY);
    }

}
