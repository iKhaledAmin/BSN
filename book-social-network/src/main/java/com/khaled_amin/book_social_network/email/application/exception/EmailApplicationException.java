package com.khaled_amin.book_social_network.email.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;

public class EmailApplicationException extends BusinessException {

    private EmailApplicationException(BaseError error, String message, Throwable cause) {
        super(error, message, cause);
    }


    public static EmailApplicationException of(EmailApplicationError error,Throwable cause) {
        return new EmailApplicationException(error, error.getMessage(), cause);
    }

    public static EmailApplicationException of(EmailApplicationError error, String customMessage, Throwable cause) {
        return new EmailApplicationException(error, customMessage, cause);
    }

    public static EmailApplicationException sendFailed(Exception ex) {
        return of(EmailApplicationError.EMAIL_SENDING_FAILED, ex);
    }

    public static EmailApplicationException renderFailed(Exception ex) {
        return of(EmailApplicationError.EMAIL_RENDERING_FAILED, ex);
    }
}
