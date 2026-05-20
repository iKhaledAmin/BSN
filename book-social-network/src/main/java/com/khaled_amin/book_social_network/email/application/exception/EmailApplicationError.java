package com.khaled_amin.book_social_network.email.application.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import org.springframework.http.HttpStatus;

public enum EmailApplicationError implements BusinessError {

    EMAIL_SENDING_FAILED("EMAIL_SENDING_FAILED", HttpStatus.INTERNAL_SERVER_ERROR, "Email sending failed"),
    EMAIL_RENDERING_FAILED("EMAIL_RENDERING_FAILED", HttpStatus.INTERNAL_SERVER_ERROR, "Email rendering failed" );


    private final String code;
    private final HttpStatus status;
    private final String message;

    EmailApplicationError(String code, HttpStatus status, String message) {
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
