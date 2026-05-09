package com.khaled_amin.book_social_network.identity.user.account.application.exception;

import com.khaled_amin.book_social_network.identity.core.exception.IdentityException;

public class AccountApplicationException extends IdentityException {

    private AccountApplicationException(AccountApplicationError error, String message) {
        super(error, message);
    }

    // -------------------- Generic -------------------- //
    public static AccountApplicationException of(AccountApplicationError error) {
        return new AccountApplicationException(error, error.getMessage());
    }

    public static AccountApplicationException of(AccountApplicationError error, String customMessage) {
        return new AccountApplicationException(error, customMessage);
    }

    // -------------------- Common -------------------- //


    public static AccountApplicationException notFound() {
        return of(AccountApplicationError.ACCOUNT_NOT_FOUND);
    }

    public static AccountApplicationException usernameAlreadyExists(){
        return of(AccountApplicationError.USERNAME_ALREADY_EXISTS);
    }

    public static AccountApplicationException emailAlreadyExists(){
        return of(AccountApplicationError.EMAIL_ALREADY_EXISTS);
    }


    public static AccountApplicationException AccountRolesNotFound() {
        return of(AccountApplicationError.ACCOUNT_ROLES_NOT_FOUND);
    }


    public static AccountApplicationException lastSuperAdmin(){
        return of(AccountApplicationError.LAST_SUPER_ADMIN);
    }

    public static AccountApplicationException invalidCommand() {
        return of(AccountApplicationError.INVALID_COMMAND);
    }

    public static AccountApplicationException invalidAccountRoleIds() {
        return of(AccountApplicationError.INVALID_ACCOUNT_ROLE_IDS);
    }

    public static AccountApplicationException invalidAccountRoles() {
        return of(AccountApplicationError.INVALID_ACCOUNT_ROLES);
    }
}