package com.khaled_amin.book_social_network.user.exception;

import com.khaled_amin.book_social_network.common.exception.BaseApiException;
import com.khaled_amin.book_social_network.user.error.AccountErrorCode;

public class AccountException extends BaseApiException {

    private AccountException(AccountErrorCode code, String message) {
        super(code, message);
    }

    public static AccountException notFound() {
        return new AccountException(
                AccountErrorCode.Account_NOT_FOUND,
                AccountErrorCode.Account_NOT_FOUND.getMessage()
        );
    }


    public static AccountException usernameAlreadyExists(String username) {
        return new AccountException(
                AccountErrorCode.USERNAME_ALREADY_EXISTS,
                AccountErrorCode.USERNAME_ALREADY_EXISTS.getMessage() + username
        );
    }

    public static AccountException emailAlreadyExists(String email) {
        return new AccountException(
                AccountErrorCode.EMAIL_ALREADY_EXISTS,
                AccountErrorCode.EMAIL_ALREADY_EXISTS.getMessage() + email
        );
    }

}