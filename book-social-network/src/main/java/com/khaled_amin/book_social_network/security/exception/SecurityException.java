package com.khaled_amin.book_social_network.security.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;

public class SecurityException extends BusinessException {
    protected SecurityException(BusinessError error) {
        super(error);
    }
    protected SecurityException(BusinessError error, String message){
        super(error,message);
    }
    protected SecurityException(BusinessError error, Throwable cause) {
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
