package com.khaled_amin.book_social_network.identity.user.account.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.policy.PolicyError;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AccountPolicyError implements PolicyError {

    CREATE_FORBIDDEN(
            SystemDomain.ACCOUNT,
            "ACCOUNT_CREATE_FORBIDDEN",
            "Create account is forbidden"
    ),

    UPDATE_FORBIDDEN(
            SystemDomain.ACCOUNT,
            "ACCOUNT_UPDATE_FORBIDDEN",
            "Update account forbidden"
    ),

    ROLE_ASSIGN_FORBIDDEN(
            SystemDomain.ACCOUNT,
            "ACCOUNT_ROLE_ASSIGN_FORBIDDEN",
            "Assign role forbidden"
    ),

    ROLE_REMOVAL_FORBIDDEN(
            SystemDomain.ACCOUNT,
            "ACCOUNT_ROLE_REMOV_FORBIDDEN",
            "Remove role is forbidden"
    ),

    ROLE_REPLACEMENT_FORBIDDEN(
            SystemDomain.ACCOUNT,
            "ACCOUNT_ROLE_REPLACE_FORBIDDEN",
            "Replace role forbidden"
    )



    ;
    private final SystemDomain domain;
    private final String code;
    private final String message;
}
