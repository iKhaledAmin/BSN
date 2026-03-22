package com.khaled_amin.book_social_network.user.error;

import com.khaled_amin.book_social_network.common.error.ApiErrorCode;
import org.springframework.http.HttpStatus;

public enum AccountErrorCode implements ApiErrorCode {

    ACCOUNT_NOT_FOUND(HttpStatus.NOT_FOUND, "Account not found"),

    USERNAME_ALREADY_EXISTS(HttpStatus.CONFLICT, "Username already exists"),

    EMAIL_ALREADY_EXISTS(HttpStatus.CONFLICT, "Email address already exists"),

    ROLE_ALREADY_ASSIGNED(HttpStatus.CONFLICT, "Role already assigned to this account"),

    ROLE_NOT_ASSIGNED(HttpStatus.CONFLICT, "Role is not assigned to this account");


    private final HttpStatus status;
    private final String message;

    AccountErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
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
