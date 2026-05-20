package com.khaled_amin.book_social_network.core.policy.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import com.khaled_amin.book_social_network.core.exception.BusinessException;


public class PolicyException extends BusinessException {

    protected PolicyException(BusinessError error, String message) {
        super(error, message);
    }

    // -------------------- Generic -------------------- //
    public static PolicyException of(BusinessError error) {
        return new PolicyException(error, error.getMessage());
    }

    public static PolicyException of(BusinessError error, String customMessage) {
        return new PolicyException(error, customMessage);
    }

    // -------------------- Common -------------------- //

    public static PolicyException invalidPolicyContext() {
        return of(PolicyError.INVALID_POLICY_CONTEXT);
    }
}
