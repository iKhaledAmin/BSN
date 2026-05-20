package com.khaled_amin.book_social_network.identity.capability.application.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import com.khaled_amin.book_social_network.identity.core.exception.IdentityException;

public class CapabilityApplicationException  extends IdentityException {
    protected CapabilityApplicationException(BusinessError error, String message) {
        super(error, message);
    }

    // -------- Generic -------- //

    public static CapabilityApplicationException of(CapabilityApplicationErrorCode error) {
        return new CapabilityApplicationException(error, error.getMessage());
    }

    public static CapabilityApplicationException of(CapabilityApplicationErrorCode error, String message) {
        return new CapabilityApplicationException(error, message);
    }

    // -------- Specific -------- //

    public static CapabilityApplicationException notFound(CapabilityApplicationErrorCode error) {
        return  CapabilityApplicationException.of(CapabilityApplicationErrorCode.NOT_FOUND);
    }


    public static CapabilityApplicationException alreadyExists() {
        return CapabilityApplicationException.of(CapabilityApplicationErrorCode.ALREADY_EXISTS);
    }
}
