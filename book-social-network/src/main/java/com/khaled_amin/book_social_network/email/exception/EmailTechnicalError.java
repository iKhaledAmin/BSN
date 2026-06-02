package com.khaled_amin.book_social_network.email.exception;

import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailTechnicalError implements TechnicalError {

    UPDATE_COMMAND_NULL(
            ErrorDomain.EMAIL,
            "EMAIL_UPDATE_COMMAND_NULL",
            "Email update command is null"
    ),

    EMAIL_NULL(
            ErrorDomain.EMAIL,
            "EMAIL_NULL",
            "Email is null"
    ),

    FAILURE_REASON_NULL(
            ErrorDomain.EMAIL,
            "EMAIL_FAILURE_REASON_NULL",
            "Failure reason is null"
    ),


    TEMPLATE_RENDERING_FAILED(
            ErrorDomain.EMAIL,
            "EMAIL_TEMPLATE_RENDERING_FAILED",
                    "Failed to render email template"
    ),

    EMAIL_SENDING_FAILED(
            ErrorDomain.EMAIL,
            "EMAIL_SENDING_FAILED",
                    "Failed to send email"
    ),


    ;
    private final ErrorDomain domain;
    private final String code;
    private final String message;
}
