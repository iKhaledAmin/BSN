package com.khaled_amin.book_social_network.identity.user.account.exception;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;


public class AccountTechnicalException extends TechnicalException {
    // -------------------------------------------- Constructors -------------------------------------------- //
    protected AccountTechnicalException(TechnicalError error) {
        super(error);
    }

//    protected AccountTechnicalException(TechnicalError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected AccountTechnicalException(TechnicalError error, String message) {
//        super(error, message);
//    }
//
//    protected AccountTechnicalException(TechnicalError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }
    // -------------------------------------------- Factory Method -------------------------------------------- //


    public static AccountTechnicalException nullAccount() {
        return new AccountTechnicalException(AccountTechnicalError.ACCOUNT_NULL);
    }

    public static AccountTechnicalException nullProfile() {
        return new AccountTechnicalException(AccountTechnicalError.PROFILE_NULL);
    }

    public static AccountTechnicalException nullRole() {
        return new AccountTechnicalException(AccountTechnicalError.ROLE_NULL);
    }

    public static AccountTechnicalException nullRoleList() {
        return new AccountTechnicalException(AccountTechnicalError.ROLE_List_NULL);
    }

    public static AccountTechnicalException nullAccountCreateCommand(){
        return new AccountTechnicalException(AccountTechnicalError.ACCOUNT_CREATE_COMMAND_NULL);
    }

    public static AccountTechnicalException nullAccountUpdateCommand() {
        return new AccountTechnicalException(AccountTechnicalError.ACCOUNT_UPDATE_COMMAND_NULL);
    }

    public static AccountTechnicalException nullProfileCreateCommand(){
        return new AccountTechnicalException(AccountTechnicalError.PROFILE_CREATE_COMMAND_NULL);
    }

    public static AccountTechnicalException nullProfileUpdateCommand() {
        return new AccountTechnicalException(AccountTechnicalError.PROFILE_UPDATE_COMMAND_NULL);
    }


    public static AccountTechnicalException profileAlreadyAttached() {
        return new AccountTechnicalException(AccountTechnicalError.PROFILE_ALREADY_ATTACHED);
    }

    public static AccountTechnicalException nullActorIdentity() {
        return new AccountTechnicalException(AccountTechnicalError.ACTOR_IDENTITY_NULL);
    }

    public static AccountTechnicalException invalidPolicyContext() {
        return new AccountTechnicalException(AccountTechnicalError.POLICY_CONTEXT_INVALID);
    }



}
