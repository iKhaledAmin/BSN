package com.khaled_amin.book_social_network.identity.capability.exception;

import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CapabilityBusinessError implements BusinessError {

    NOT_FOUND(
            ErrorDomain.CAPABILITY,
            "CAPABILITY_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Capability not found"
    ),


    ;

    private final ErrorDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;


}