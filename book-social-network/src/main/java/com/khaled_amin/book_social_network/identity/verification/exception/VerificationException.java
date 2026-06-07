package com.khaled_amin.book_social_network.identity.verification.exception;

import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.business.BusinessException;

public class VerificationException extends BusinessException {


    protected VerificationException(BusinessError error) {
        super(error);
    }

//    protected VerificationException(BusinessError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected VerificationException(BusinessError error, String message) {
//        super(error, message);
//    }
//
//    protected VerificationException(BusinessError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }



    public static VerificationException invalidToken() {
        return new VerificationException(VerificationError.TOKEN_CODE_INVALID);
    }

    public static VerificationException expired() {
        return new VerificationException(VerificationError.TOKEN_EXPIRED);
    }

    public static VerificationException alreadyVerified() {
        return new VerificationException(VerificationError.TOKEN_ALREADY_USED);
    }
}