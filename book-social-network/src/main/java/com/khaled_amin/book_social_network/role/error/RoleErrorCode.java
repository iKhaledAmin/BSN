package com.khaled_amin.book_social_network.role.error;

import com.khaled_amin.book_social_network.common.error.ApiErrorCode;
import org.springframework.http.HttpStatus;

public enum RoleErrorCode implements ApiErrorCode {

    ROLE_NOT_FOUND(HttpStatus.NOT_FOUND, "Role not found"),
    ROLE_ALREADY_EXISTS(HttpStatus.CONFLICT, "Role already exists"),
    DEFAULT_ROLE_NOT_CONFIGURED(HttpStatus.INTERNAL_SERVER_ERROR, "Default role is not configured");

    private final HttpStatus status;
    private final String message;

    RoleErrorCode(HttpStatus status, String message) {
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
