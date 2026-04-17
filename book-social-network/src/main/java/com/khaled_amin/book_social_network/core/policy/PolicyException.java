package com.khaled_amin.book_social_network.core.policy;

import com.khaled_amin.book_social_network.core.exception.BaseError;
import com.khaled_amin.book_social_network.core.exception.BaseException;


public class PolicyException extends BaseException {

    protected PolicyException(BaseError error, String message) {
        super(error, message);
    }

    // -------------------- Generic -------------------- //
    public static PolicyException of(BaseError error) {
        return new PolicyException(error, error.getMessage());
    }

    public static PolicyException of(BaseError error, String customMessage) {
        return new PolicyException(error, customMessage);
    }

    // -------------------- Common -------------------- //

    public static PolicyException invalidPolicyContext() {
        return of(PolicyError.INVALID_POLICY_CONTEXT);
    }
}
