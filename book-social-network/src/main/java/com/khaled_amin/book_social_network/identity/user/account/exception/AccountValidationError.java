package com.khaled_amin.book_social_network.identity.user.account.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountValidationError implements ValidationError {

    ID_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_ID_INVALID",
            "Account id is invalid"
    ),

    FIRST_NAME_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_FIRST_NAME_INVALID",
            "Account first name is invalid"
    ),

    LAST_NAME_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_LAST_NAME_INVALID",
            "Account last name is invalid"
    ),

    USERNAME_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_USERNAME_INVALID",
            "Account username is invalid"
    ),

    EMAIL_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_EMAIL_INVALID",
            "Account email is invalid"
    ),

    PHONE_NUMBER_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_PHONE_NUMBER_INVALID",
            "Account phone number is invalid"
    ),

    PASSWORD_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_PASSWORD_INVALID",
            "Account password is invalid"
    ),

    BIRTH_DATE_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_BIRTH_DATE_INVALID",
            "Account birth date is invalid"
    ),

    PROFESSION_INVALID(
            SystemDomain.ACCOUNT,
            "ACCOUNT_PROFESSION_INVALID",
            "Account profession is invalid"
    ),


    ;

    private final SystemDomain domain;
    private final String code;
    private final String message;
}