package com.khaled_amin.book_social_network.user.application.exception;
import com.khaled_amin.book_social_network.core.policy.PolicyException;


public class AccountPolicyException extends PolicyException {

    private AccountPolicyException(AccountPolicyError error, String message) {
        super(error, message);
    }

    // -------------------- Generic -------------------- //

    public static AccountPolicyException of(AccountPolicyError error) {
        return new AccountPolicyException(error, error.getMessage());
    }


        public static AccountPolicyException of(AccountPolicyError code, String customMessage) {
        return new AccountPolicyException(code, customMessage);
    }


    // -------------------- Common -------------------- //

    public static AccountPolicyException invalidPolicyContext(String message) {
        return of(AccountPolicyError.INVALID_POLICY_CONTEXT, message);
    }



    public static AccountPolicyException assignRoleForbidden() {
        return of(AccountPolicyError.ROLE_ASSIGN_FORBIDDEN);
    }


    public static AccountPolicyException updateForbidden() {
        return of(AccountPolicyError.UPDATE_FORBIDDEN);
    }

    public static AccountPolicyException createForbidden() {
        return of(AccountPolicyError.CREATE_FORBIDDEN);
    }


    public static AccountPolicyException roleRemovalForbidden() {
        return of(AccountPolicyError.ROLE_REMOVAL_FORBIDDEN);
    }

    public static AccountPolicyException roleReplacementForbidden() {
        return of(AccountPolicyError.ROLE_REPLACEMENT_FORBIDDEN);
    }
}
