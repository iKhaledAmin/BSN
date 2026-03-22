package com.khaled_amin.book_social_network.user.exception;

import com.khaled_amin.book_social_network.common.exception.BaseApiException;
import com.khaled_amin.book_social_network.user.error.AccountErrorCode;

public class AccountException extends BaseApiException {

    private AccountException(AccountErrorCode code, String message) {
        super(code, message);
    }

    // -------------------- Generic -------------------- //

    public static AccountException of(AccountErrorCode code) {
        return new AccountException(code, code.getMessage());
    }

    public static AccountException of(AccountErrorCode code, String message) {
        return new AccountException(code, message);
    }

    // -------------------- Common -------------------- //

    public static AccountException notFound() {
        return of(AccountErrorCode.ACCOUNT_NOT_FOUND);
    }

    public static AccountException roleAlreadyAssigned() {
        return of(AccountErrorCode.ROLE_ALREADY_ASSIGNED);
    }

    public static AccountException roleNotAssigned() {
        return of(AccountErrorCode.ROLE_NOT_ASSIGNED);
    }

    // -------------------- Dynamic -------------------- //

    public static AccountException usernameAlreadyExists(String username) {
        return of(
                AccountErrorCode.USERNAME_ALREADY_EXISTS,
                AccountErrorCode.USERNAME_ALREADY_EXISTS.getMessage() + username
        );
    }

    public static AccountException emailAlreadyExists(String email) {
        return of(
                AccountErrorCode.EMAIL_ALREADY_EXISTS,
                AccountErrorCode.EMAIL_ALREADY_EXISTS.getMessage() + email
        );
    }
}