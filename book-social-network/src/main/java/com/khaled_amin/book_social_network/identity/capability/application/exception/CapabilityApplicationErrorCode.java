package com.khaled_amin.book_social_network.identity.capability.application.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum CapabilityApplicationErrorCode implements BusinessError {

    NOT_FOUND("CAPABILITY_NOT_FOUND", HttpStatus.NOT_FOUND, "Capability not found"),
    ALREADY_EXISTS("CAPABILITY_ALREADY_EXISTS", HttpStatus.CONFLICT, "Capability already exists");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
