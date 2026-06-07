package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import com.khaled_amin.book_social_network.core.exception.security.SecurityException;

public class AuthenticationException extends SecurityException {

    // --------------------------------------------- Constructors --------------------------------------------- //

    protected AuthenticationException(SecurityError error) {
        super(error);
    }

    protected AuthenticationException(SecurityError error, Throwable cause) {
        super(error, cause);
    }

    protected AuthenticationException(SecurityError error, String message) {
        super(error, message);
    }

    protected AuthenticationException(SecurityError error, String message, Throwable cause) {
        super(error, message, cause);
    }

    // --------------------------------------------- Factories --------------------------------------------- //


    // ================= CREDENTIALS =================

    public static AuthenticationException invalidCredentials() {
        return new AuthenticationException(AuthenticationError.CREDENTIALS_INVALID);
    }

    public static AuthenticationException authenticationFailed() {
        return new AuthenticationException(AuthenticationError.AUTHENTICATION_FAILED);
    }

    // ================= PRINCIPAL =================

    public static AuthenticationException principalNotFound(String message) {
        return new AuthenticationException(AuthenticationError.PRINCIPAL_NOT_FOUND, message);
    }

    // ================= TOKEN =================

    public static AuthenticationException invalidToken() {
        return new AuthenticationException(AuthenticationError.TOKEN_INVALID);
    }

    public static AuthenticationException invalidToken(Throwable cause) {
        return new AuthenticationException(AuthenticationError.TOKEN_INVALID, cause);
    }

    public static AuthenticationException expiredToken() {
        return new AuthenticationException(AuthenticationError.TOKEN_EXPIRED);
    }

    public static AuthenticationException expiredToken(Throwable cause) {
        return new AuthenticationException(AuthenticationError.TOKEN_EXPIRED, cause);
    }

    public static AuthenticationException malformedToken(Throwable cause) {
        return new AuthenticationException(AuthenticationError.TOKEN_MALFORMED, cause);
    }

    public static AuthenticationException invalidTokenSignature(Throwable cause) {
        return new AuthenticationException(
                AuthenticationError.TOKEN_SIGNATURE_INVALID,
                cause
        );
    }

    public static AuthenticationException missingToken() {
        return new AuthenticationException(AuthenticationError.TOKEN_MISSING);
    }


    public static AuthenticationException principalLocked(String principalType) {
        return new AuthenticationException(
                AuthenticationError.PRINCIPAL_LOCKED,
                principalType + " is locked"
        );
    }

    public static AuthenticationException principalInactive(String principalType) {
        return new AuthenticationException(AuthenticationError.PRINCIPAL_INACTIVE,
                principalType + " is not active"
        );
    }
}