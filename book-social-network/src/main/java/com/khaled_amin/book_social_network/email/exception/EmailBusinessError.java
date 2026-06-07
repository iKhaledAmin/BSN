package com.khaled_amin.book_social_network.email.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum EmailBusinessError implements BusinessError {


    UPDATE_NOT_ALLOWED(
            SystemDomain.EMAIL,
            "EMAIL_UPDATE_NOT_ALLOWED",
            HttpStatus.CONFLICT,
            "Email update not allowed"
    ),

    TRANSITION_INVALID(
            SystemDomain.EMAIL,
            "EMAIL_TRANSITION_INVALID",
            HttpStatus.CONFLICT,
            "Invalid email state transition"
    )


    ;
    private final SystemDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;
}
