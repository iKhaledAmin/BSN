package com.khaled_amin.book_social_network.email.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;

public class EmailDomainException extends BusinessException {

    private EmailDomainException(BaseError error, String message) {
        super(error, message);
    }


    // ---------- Generic ---------- //

    public static EmailDomainException of(EmailDomainError error) {
        return new EmailDomainException(error, error.getMessage());
    }

    public static EmailDomainException of(EmailDomainError error, String customMessage) {
        return new EmailDomainException(error, customMessage);
    }

    // ---------- Specific ---------- //


    public static EmailDomainException invalidEmailAddress() {
        return of(EmailDomainError.INVALID_EMAIL_ADDRESS);
    }

    public static EmailDomainException invalidSubject() {
        return of(EmailDomainError.INVALID_SUBJECT);
    }

    public static EmailDomainException invalidBody() {
        return of(EmailDomainError.INVALID_BODY);
    }

    public static EmailDomainException invalidTemplate() {
        return of(EmailDomainError.INVALID_TEMPLATE);
    }


    public static EmailDomainException invalidUpdateCommand() {
        return of(EmailDomainError.INVALID_UPDATE_COMMAND);
    }

    public static EmailDomainException invalidState() {
        return of(EmailDomainError.INVALID_STATE);
    }

    public static EmailDomainException updateViolation() {
        return of(EmailDomainError.UPDATE_EMAIL_VIOLATION);
    }

    public static EmailDomainException invalidFailureReason() {
        return of(EmailDomainError.INVALID_FAILURE_REASON);
    }

    public static EmailDomainException invalidedTransition() {
        return of(EmailDomainError.INVALIDED_TRANSITION);
    }
}
