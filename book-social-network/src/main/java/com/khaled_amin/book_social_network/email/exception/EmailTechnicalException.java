package com.khaled_amin.book_social_network.email.exception;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;

public class EmailTechnicalException extends TechnicalException {
    // -------------------------------------------- Constructors -------------------------------------------- //

    protected EmailTechnicalException(TechnicalError error) {
        super(error);
    }

    protected EmailTechnicalException(TechnicalError error, Throwable cause) {
        super(error, cause);
    }

//    protected EmailTechnicalException(TechnicalError error, String message) {
//        super(error, message);
//    }
//
//    protected EmailTechnicalException(TechnicalError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }

    // -------------------------------------------- Factories -------------------------------------------- //

    public static EmailTechnicalException nullUpdateCommand() {
        return new EmailTechnicalException(EmailTechnicalError.UPDATE_COMMAND_NULL);
    }

    public static EmailTechnicalException nullEmail() {
        return new EmailTechnicalException(EmailTechnicalError.EMAIL_NULL);
    }

    public static EmailTechnicalException nullFailureReason() {
        return new EmailTechnicalException(EmailTechnicalError.FAILURE_REASON_NULL);
    }

    public static EmailTechnicalException templateRenderingFailed(Throwable cause) {
        return new EmailTechnicalException(EmailTechnicalError.TEMPLATE_RENDERING_FAILED, cause);
    }

    public static EmailTechnicalException emailSendingFailed(Throwable cause) {
        return new EmailTechnicalException(EmailTechnicalError.EMAIL_SENDING_FAILED, cause);
    }
}
