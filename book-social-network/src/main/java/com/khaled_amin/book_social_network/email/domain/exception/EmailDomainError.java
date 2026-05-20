package com.khaled_amin.book_social_network.email.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import org.springframework.http.HttpStatus;

public enum EmailDomainError implements BusinessError {

    UPDATE_EMAIL_VIOLATION("EMAIL_UPDATE_VIOLATION", HttpStatus.CONFLICT, "Cannot update email"),
    INVALIDED_TRANSITION("INVALIDED_TRANSITION", HttpStatus.CONFLICT, "Email live cycle violation"),

    INVALID_EMAIL("EMAIL_INVALID", HttpStatus.BAD_REQUEST, "Invalid email"),
    INVALID_EMAIL_ADDRESS("EMAIL_INVALID_EMAIL_ADDRESS_FORMAT", HttpStatus.BAD_REQUEST, "Invalid email address format"),
    INVALID_SUBJECT("EMAIL_INVALID_SUBJECT", HttpStatus.BAD_REQUEST, "Invalid email subject"),
    INVALID_BODY("EMAIL_INVALID_BODY", HttpStatus.BAD_REQUEST, "Invalid email body"),
    INVALID_TEMPLATE("EMAIL_INVALID_TEMPLATE", HttpStatus.BAD_REQUEST, "Invalid email template"),
    INVALID_UPDATE_COMMAND("EMAIL_INVALID_UPDATE_COMMAND", HttpStatus.BAD_REQUEST, "Invalid update command"),
    INVALID_FAILURE_REASON("EMAIL_INVALID_FAILURE_REASON", HttpStatus.BAD_REQUEST, "Invalid failure reason"),
    INVALID_STATE("EMAIL_INVALID_STATE", HttpStatus.BAD_REQUEST, "Invalid email state");







    private final String code;
    private final HttpStatus status;
    private final String message;

    EmailDomainError(String code, HttpStatus status, String message) {
        this.code = code;
        this.status = status;
        this.message = message;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
