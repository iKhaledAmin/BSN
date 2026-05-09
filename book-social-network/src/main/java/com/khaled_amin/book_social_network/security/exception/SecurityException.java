package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;

public class SecurityException extends BusinessException {
    protected SecurityException(BaseError error) {
        super(error);
    }
    protected SecurityException(BaseError error, Throwable cause) {
        super(error, cause);
    }




    // ================= INVALID CREDENTIALS =================

    public static SecurityException invalidCredentials() {
        return new SecurityException(
                SecurityError.INVALID_CREDENTIALS
        );
    }

    // ================= DISABLED =================

    public static SecurityException principalDisabled() {
        return new SecurityException(
                SecurityError.PRINCIPAL_DISABLED
        );
    }

    // ================= LOCKED =================

    public static SecurityException principalLocked() {
        return new SecurityException(
                SecurityError.PRINCIPAL_LOCKED
        );
    }
}
