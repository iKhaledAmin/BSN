package com.khaled_amin.book_social_network.identity.user.account.exception;

import com.khaled_amin.book_social_network.core.exception.policy.PolicyError;
import com.khaled_amin.book_social_network.core.exception.policy.PolicyException;

public class AccountPolicyException extends PolicyException {
    // -------------------------------------------- Constructors -------------------------------------------- //
    protected AccountPolicyException(PolicyError error) {
        super(error);
    }

//    protected AccountPolicyException(PolicyError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected AccountPolicyException(PolicyError error, String message) {
//        super(error, message);
//    }
//
//    protected AccountPolicyException(PolicyError error, String message, Throwable cause) {
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
