package com.khaled_amin.book_social_network.email.exception;

import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum EmailValidationError implements ValidationError {

    EMAIL_ADDRESS_INVALID(
            ErrorDomain.EMAIL,
            "EMAIL_ADDRESS_INVALID",
            "Invalid email address"
    ),

    SUBJECT_INVALID(
            ErrorDomain.EMAIL,
            "EMAIL_SUBJECT_INVALID",
            "Invalid email subject"
    ),

    BODY_INVALID(
            ErrorDomain.EMAIL,
            "EMAIL_BODY_INVALID",
            "Invalid email body"
    ),

    TEMPLATE_INVALID(
            ErrorDomain.EMAIL,
            "EMAIL_TEMPLATE_INVALID",
            "Invalid email template"
    ),




    ;

    private final ErrorDomain domain;
    private final String code;
    private final String message;
}