package com.khaled_amin.book_social_network.identity.user.role.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum RoleDomainError implements BaseError {

    INVALID_ROLE_ID(
            "ROLE_INVALID_ROLE_ID",
            HttpStatus.BAD_REQUEST,
            "Role id is invalid"
    ),

    INVALID_ROLE_NAME(
            "ROLE_INVALID_NAME",
            HttpStatus.BAD_REQUEST,
            "Role value is invalid"
    ),

    INVALID_ROLE_DISPLAY_NAME(
            "ROLE_INVALID_DISPLAY_NAME",
            HttpStatus.BAD_REQUEST,
            "Role display value is invalid"
    ),

    INVALID_ROLE_DESCRIPTION(
            "ROLE_INVALID_DESCRIPTION",
            HttpStatus.BAD_REQUEST,
            "Role value is invalid"
    ),

    INVALID_ROLE_TYPE(
            "ROLE_INVALID_ROLE_TYPE",
            HttpStatus.BAD_REQUEST,
            "Role type is invalid"
    ),

    INVALID_SYSTEM_ROLE(
            "ROLE_INVALID_SYSTEM_ROLE",
            HttpStatus.BAD_REQUEST,
            "System role is invalid"
    ),

    INVALID_COMMAND(
            "ROLE_INVALID_COMMAND",
            HttpStatus.BAD_REQUEST,
            "Role command object is invalid"
    ),


    INVALID_ROLE_STATE(
            "ROLE_INVALID_STATE",
            HttpStatus.BAD_REQUEST,
            "Role state is invalid"
    ),

    PROTECTED_ROLE_VIOLATION(
            "ROLE_PROTECTED_VIOLATION",
            HttpStatus.CONFLICT,
            "This role should be protected"
    );




    private final String code;
    private final HttpStatus status;
    private final String message;

    RoleDomainError(String code, HttpStatus status, String message) {
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
