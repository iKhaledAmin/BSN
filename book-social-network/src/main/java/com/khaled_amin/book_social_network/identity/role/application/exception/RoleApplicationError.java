package com.khaled_amin.book_social_network.role.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;


public enum RoleApplicationError implements BaseError {

    ALREADY_EXISTS(
            "ROLE_ALREADY_EXISTS",
            HttpStatus.CONFLICT,
            "Role already exists"
    ),

    DELETION_VIOLATION(
            "ROLE_DELETION_VIOLATION",
            HttpStatus.CONFLICT,
            "Role cannot be deleted"
    ),

    NOT_FOUND(
            "ROLE_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Role not found"
    ),

    DEFAULT_ROLE_NOT_CONFIGURED(
            "ROLE_DEFAULT_NOT_CONFIGURED",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Default role is not configured"
    ),

    INVALID_COMMAND(
            "ROLE_INVALID_COMMAND",
            HttpStatus.BAD_REQUEST,
            "Command must not be null"
    ),

    ROLES_NOT_FOUND(
            "ROLE_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "One or more roles not found"
    ),

    INVALID_SYSTEM_ROLE(
            "ROLE_INVALID_SYSTEM_ROLE",
            HttpStatus.BAD_REQUEST,
            "System role must not be null"
    );

    private final String code;
    private final HttpStatus status;
    private final String message;

    RoleApplicationError(String code, HttpStatus status, String message) {
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