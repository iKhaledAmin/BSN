package com.khaled_amin.book_social_network.identity.user.account.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import org.springframework.http.HttpStatus;


public enum AccountPolicyError implements BaseError {

    INVALID_POLICY_CONTEXT(
            "ACCOUNT_POLICY_INVALID_CONTEXT",
            HttpStatus.INTERNAL_SERVER_ERROR,
            "Invalid policy context"
    ),


    CREATE_FORBIDDEN(
            "ACCOUNT_POLICY_CREATE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "You cannot resolve this account"
    ),

    UPDATE_FORBIDDEN(
            "ACCOUNT_POLICY_UPDATE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "You cannot update this account"
    ),

    ROLE_ASSIGN_FORBIDDEN(
            "ACCOUNT_ROLE_ASSIGN_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "You cannot assign this role"
    ),

   ROLE_REMOVAL_FORBIDDEN(
            "ACCOUNT_POLICY_ROLE_REMOVAL_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "You cannot remove this role"
    ),

    ROLE_REPLACEMENT_FORBIDDEN(
            "ACCOUNT_POLICY_ROLE_REPLACEMENT_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "You cannot replace this role"
    );



    private final String code;
    private final HttpStatus status;
    private final String message;

    AccountPolicyError(String code, HttpStatus status, String message) {
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