package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;

public class InvalidTokenException extends SecurityException {

    protected InvalidTokenException(BusinessError error) {
        super(error);
    }

    protected InvalidTokenException(BusinessError error, Throwable cause) {
        super(error, cause);
    }

    // ================= Generic =================

    public static InvalidTokenException invalid() {
        return new InvalidTokenException(SecurityError.TOKEN_INVALID);
    }

    public static InvalidTokenException invalid(Throwable cause) {
        return new InvalidTokenException(SecurityError.TOKEN_INVALID, cause);
    }

    // ================= Expired =================

    public static InvalidTokenException expired(Throwable cause) {
        return new InvalidTokenException(SecurityError.TOKEN_EXPIRED, cause);
    }

    // ================= Malformed =================


    public static InvalidTokenException malformed(Throwable cause) {
        return new InvalidTokenException(SecurityError.TOKEN_MALFORMED, cause);
    }

    // ================= Signature =================


    public static InvalidTokenException signatureInvalid(Throwable cause) {
        return new InvalidTokenException(SecurityError.TOKEN_SIGNATURE_INVALID, cause);
    }

}