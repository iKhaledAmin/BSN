package com.khaled_amin.book_social_network.identity.user.account.exception;

import com.khaled_amin.book_social_network.core.exception.core.ErrorDomain;
import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AccountPolicyError implements SecurityError {

    CREATE_FORBIDDEN(
            ErrorDomain.ACCOUNT,
            "ACCOUNT_CREATE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Create account is forbidden"
    ),

    UPDATE_FORBIDDEN(
            ErrorDomain.ACCOUNT,
            "ACCOUNT_UPDATE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Update account forbidden"
    ),

    ROLE_ASSIGN_FORBIDDEN(
            ErrorDomain.ACCOUNT,
            "ACCOUNT_ROLE_ASSIGN_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Assign role forbidden"
    ),

    ROLE_REMOVAL_FORBIDDEN(
            ErrorDomain.ACCOUNT,
            "ACCOUNT_ROLE_REMOV_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Remove role is forbidden"
    ),

    ROLE_REPLACEMENT_FORBIDDEN(
            ErrorDomain.ACCOUNT,
            "ACCOUNT_ROLE_REPLACE_FORBIDDEN",
            HttpStatus.FORBIDDEN,
            "Replace role forbidden"
    )



    ;
    private final ErrorDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;
}
