package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;

public class SecurityException extends BusinessException {
    protected SecurityException(BaseError error) {
        super(error);
    }
    protected SecurityException(BaseError error,String message){
        super(error,message);
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

    // ================= LOCKED =================

    public static SecurityException principalLocked(String message) {
        return new SecurityException(
                SecurityError.PRINCIPAL_LOCKED,
                message
        );
    }

    // ================= NOT ACTIVE =================

    public static SecurityException principalNotActive(String message) {
        return new SecurityException(
                SecurityError.PRINCIPAL_NOT_ACTIVE,
                message
        );
    }


}
