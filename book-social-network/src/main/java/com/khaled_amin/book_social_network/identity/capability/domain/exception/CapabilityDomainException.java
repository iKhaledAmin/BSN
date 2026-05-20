package com.khaled_amin.book_social_network.identity.capability.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BusinessError;
import com.khaled_amin.book_social_network.identity.core.exception.IdentityException;

public class CapabilityDomainException extends IdentityException {
    protected CapabilityDomainException(BusinessError error, String message) {
        super(error, message);
    }


    // -------- Generic -------- //

    public static CapabilityDomainException of(CapabilityDomainErrorCode error) {
        return new CapabilityDomainException(error, error.getMessage());
    }

    public static CapabilityDomainException of(CapabilityDomainErrorCode error, String message) {
        return new CapabilityDomainException(error, message);
    }

    // -------- Specific -------- //

    public static CapabilityDomainException invalidCapability() {
        return of(CapabilityDomainErrorCode.INVALID_CAPABILITY);
    }

    public static CapabilityDomainException invalidCommand() {
        return of(CapabilityDomainErrorCode.INVALID_COMMAND);
    }
}


