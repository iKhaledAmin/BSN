package com.khaled_amin.book_social_network.identity.core.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum IdentityError implements BusinessError {
    IDENTITY_NOT_FOUND("IDENTITY_NOT_FOUND", HttpStatus.NOT_FOUND, "Identity not found"),
    INVALID_IDENTITY("IDENTITY_INVALID", HttpStatus.BAD_REQUEST, "Invalid identity");

    private final String code;
    private final HttpStatus status;
    private final String message;

}
