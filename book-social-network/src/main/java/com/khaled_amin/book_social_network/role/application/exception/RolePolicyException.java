package com.khaled_amin.book_social_network.role.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseException;

public class RolePolicyException extends BaseException {

    private RolePolicyException(RolePolicyError error, String message) {
        super(error, message);
    }


    // -------- Generic -------- //

    public static RolePolicyException of(RolePolicyError error){
        return new RolePolicyException(error, error.getMessage());
    }

    public static RolePolicyException of(RolePolicyError error , String customMessage){
        return new RolePolicyException(error, customMessage);
    }


    // -------- Specific -------- //

    public static RolePolicyException invalidPolicyContext(String message) {
        return of(RolePolicyError.INVALID_POLICY_CONTEXT, message);
    }

    public static RolePolicyException createBusinessRoleForbidden() {
        return of(RolePolicyError.CREATE_BUSINESS_ROLE_FORBIDDEN);
    }

    public static RolePolicyException createSystemRoleForbidden() {
        return of(RolePolicyError.CREATE_SYSTEM_ROLE_FORBIDDEN);
    }

    public static RolePolicyException deleteForbidden() {
        return of(RolePolicyError.DELETE_FORBIDDEN);
    }

    public static RolePolicyException updateForbidden() {
        return of(RolePolicyError.UPDATE_FORBIDDEN);
    }


}
