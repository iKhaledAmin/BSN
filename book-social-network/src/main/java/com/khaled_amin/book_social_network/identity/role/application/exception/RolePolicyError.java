package com.khaled_amin.book_social_network.role.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;

public enum RolePolicyError implements BaseError {


    INVALID_POLICY_CONTEXT(
            "ROLE_POLICY_INVALID_CONTEXT",
            HttpStatus.BAD_REQUEST,
            "Invalid policy context"
    ),

    CREATE_BUSINESS_ROLE_FORBIDDEN(
            "ROLE_POLICY_CREATE_BUSINESS_ROLE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Creating role is not allowed"
    ),

    CREATE_SYSTEM_ROLE_FORBIDDEN(
            "ROLE_POLICY_CREATE_SYSTEM_ROLE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Creating system roles is not allowed"
    ),


    DELETE_FORBIDDEN(
            "ROLE_POLICY_DELETE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Deleting roles is not allowed"
    )
    , UPDATE_FORBIDDEN(
            "ROLE_POLICY_UPDATE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Updating roles is not allowed"
    );





    private final String code;
    private final HttpStatus status;
    private final String message;

    RolePolicyError(String code, HttpStatus status, String message) {
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
