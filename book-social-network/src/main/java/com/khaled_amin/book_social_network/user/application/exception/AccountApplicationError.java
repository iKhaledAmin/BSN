package com.khaled_amin.book_social_network.user.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum AccountApplicationError implements BaseError {

    ACCOUNT_NOT_FOUND(
            "ACCOUNT_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Account not found"
    ),

    USERNAME_ALREADY_EXISTS(
            "ACCOUNT_USERNAME_ALREADY_EXISTS",
            HttpStatus.CONFLICT,
            "Username already exists"
    ),

    EMAIL_ALREADY_EXISTS(
            "ACCOUNT_EMAIL_ALREADY_EXISTS",
            HttpStatus.CONFLICT,
            "Email address already exists"
    ),

    ACCOUNT_ROLES_NOT_FOUND(
            "ACCOUNT_ROLES_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "One or more roles not found"
    ),


    LAST_SUPER_ADMIN(
            "ACCOUNT_LAST_SUPER_ADMIN",
            HttpStatus.CONFLICT,
            "Cannot remove the last SUPER_ADMIN in the system"
    ),

    INVALID_COMMAND(
            "ACCOUNT_INVALID_COMMAND",
            HttpStatus.BAD_REQUEST,
            "Invalid command"
    ),

    INVALID_ACCOUNT_ROLE_IDS(
            "ACCOUNT_INVALID_ACCOUNT_ROLE_IDS",
            HttpStatus.BAD_REQUEST,
            "Invalid role IDs"
    );

    private final String code;
    private final HttpStatus status;
    private final String message;

    AccountApplicationError(String code, HttpStatus status, String message) {
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
