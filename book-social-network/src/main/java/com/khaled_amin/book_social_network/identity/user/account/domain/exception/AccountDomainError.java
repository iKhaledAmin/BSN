package com.khaled_amin.book_social_network.identity.user.account.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import org.springframework.http.HttpStatus;

public enum AccountDomainError implements BusinessError {

    ROLE_ALREADY_ASSIGNED(
            "ACCOUNT_ROLE_ALREADY_ASSIGNED",
            HttpStatus.CONFLICT,
            "Role already assigned"
    ),

    ROLE_NOT_ASSIGNED(
            "ACCOUNT_ROLE_NOT_ASSIGNED",
            HttpStatus.CONFLICT,
            "Role not assigned"
    ),

    PROFILE_ALREADY_ATTACHED(
            "ACCOUNT_PROFILE_ALREADY_ATTACHED",
            HttpStatus.CONFLICT,
            "Profile already attached"
    ),

    INVALID_PROFILE(
            "ACCOUNT_INVALID_PROFILE",
            HttpStatus.BAD_REQUEST,
            "Invalid profile"
    ),

    INVALID_FIRST_NAME(
            "ACCOUNT_INVALID_FIRST_NAME",
            HttpStatus.BAD_REQUEST,
            "Invalid first value"
    ),

    INVALID_LAST_NAME(
            "ACCOUNT_INVALID_LAST_NAME",
            HttpStatus.BAD_REQUEST,
            "Invalid last value"
    ),

    INVALID_USERNAME(
            "ACCOUNT_INVALID_USERNAME",
            HttpStatus.BAD_REQUEST,
            "Invalid username"
    ),

    INVALID_PASSWORD(
            "ACCOUNT_INVALID_PASSWORD",
            HttpStatus.BAD_REQUEST,
            "Invalid encodedPassword"
    ),

    INVALID_EMAIL(
            "ACCOUNT_INVALID_EMAIL",
            HttpStatus.BAD_REQUEST,
            "Invalid email"
    ),

    INVALID_ROLE(
    "ACCOUNT_INVALID_ROLE",
    HttpStatus.BAD_REQUEST,
    "Invalid role"
    ),

    INVALID_ROLES(
            "ACCOUNT_INVALID_ROLES",
            HttpStatus.BAD_REQUEST,
            "Invalid roles"
    ),

    INVALID_ACCOUNT(
    "ACCOUNT_INVALID_ACCOUNT",
    HttpStatus.BAD_REQUEST,
    "Invalid account"
    ),

    INVALID_PHONE_NUMBER(
            "ACCOUNT_INVALID_PHONE_NUMBER",
            HttpStatus.BAD_REQUEST,
            "Invalid phone number"
    ),

    INVALID_BIRTH_DATE(
            "ACCOUNT_INVALID_BIRTH_DATE",
            HttpStatus.BAD_REQUEST,
            "Invalid birth date"
    ),

    EMPTY_ROLES(
    "ACCOUNT_EMPTY_ROLES",
    HttpStatus.CONFLICT,
    "Account must have at least one role"
    ),

    MISSING_SYSTEM_ROLE(
            "ACCOUNT_MISSING_SYSTEM_ROLE",
            HttpStatus.CONFLICT,
            "Account must have at least one system role"
    ),

    DUPLICATE_ROLES(
            "ACCOUNT_DUPLICATE_ROLES",
            HttpStatus.CONFLICT,
            "Account cannot have duplicate roles"
    ),

    INVALID_PROFESSION(
            "ACCOUNT_INVALID_PROFESSION",
            HttpStatus.BAD_REQUEST,
            "Invalid profession"
    ),

    INVALID_PROFILE_STATUS(
            "ACCOUNT_INVALID_PROFILE_STATUS",
            HttpStatus.BAD_REQUEST,
            "Invalid profile status"
    ),

    INVALID_ACCOUNT_STATUS(
            "ACCOUNT_INVALID_ACCOUNT_STATUS",
            HttpStatus.BAD_REQUEST,
            "Invalid account status"
    ),

    INVALID_ACCOUNT_ID(
            "ACCOUNT_INVALID_ACCOUNT_ID",
            HttpStatus.BAD_REQUEST,
            "Invalid account id"
    ),

    INVALID_ACCOUNT_STATE(
            "ACCOUNT_INVALID_ACCOUNT_STATE",
            HttpStatus.BAD_REQUEST,
            "Invalid account state"
    ),

    INVALID_NEW_PASSWORD(
            "ACCOUNT_INVALID_NEW_PASSWORD",
            HttpStatus.BAD_REQUEST,
            "Invalid new password"
    );





    private final String code;
    private final HttpStatus status;
    private final String message;

    AccountDomainError(String code, HttpStatus status, String message) {
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
