package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import com.khaled_amin.book_social_network.core.exception.security.SecurityException;

public class AuthorizationException extends SecurityException {

    // --------------------------------------------- Constructors --------------------------------------------- //

    protected AuthorizationException(SecurityError error) {
        super(error);
    }

    protected AuthorizationException(SecurityError error, Throwable cause) {
        super(error, cause);
    }

    protected AuthorizationException(SecurityError error, String message) {
        super(error, message);
    }

    protected AuthorizationException(SecurityError error,
                                     String message,
                                     Throwable cause) {
        super(error, message, cause);
    }

    // --------------------------------------------- Factories --------------------------------------------- //

    // ================= ACCESS =================

    public static AuthorizationException accessDenied() {
        return new AuthorizationException(AuthorizationError.ACCESS_DENIED);
    }

    public static AuthorizationException insufficientScope() {
        return new AuthorizationException(AuthorizationError.INSUFFICIENT_SCOPE);
    }


}