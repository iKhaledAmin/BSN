package com.khaled_amin.book_social_network.identity.user.account.exception;

import com.khaled_amin.book_social_network.core.exception.security.SecurityError;
import com.khaled_amin.book_social_network.core.exception.security.SecurityException;

public class AccountPolicyException extends SecurityException {
    // -------------------------------------------- Constructors -------------------------------------------- //
    protected AccountPolicyException(SecurityError error) {
        super(error);
    }

//    protected AccountPolicyException(SecurityError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected AccountPolicyException(SecurityError error, String message) {
//        super(error, message);
//    }
//
//    protected AccountPolicyException(SecurityError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }

    // -------------------------------------------- Factory Method -------------------------------------------- //



    public static AccountPolicyException assignRoleForbidden() {
        return new AccountPolicyException(AccountPolicyError.ROLE_ASSIGN_FORBIDDEN);
    }


    public static AccountPolicyException updateForbidden() {
        return new AccountPolicyException(AccountPolicyError.UPDATE_FORBIDDEN);
    }

    public static AccountPolicyException createForbidden() {
        return new AccountPolicyException(AccountPolicyError.CREATE_FORBIDDEN);
    }


    public static AccountPolicyException roleRemovalForbidden() {
        return new AccountPolicyException(AccountPolicyError.ROLE_REMOVAL_FORBIDDEN);
    }

    public static AccountPolicyException roleReplacementForbidden() {
        return new AccountPolicyException(AccountPolicyError.ROLE_REPLACEMENT_FORBIDDEN);
    }

}
