package com.khaled_amin.book_social_network.identity.capability.exception;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;

public class CapabilityTechnicalException extends TechnicalException {

    // ----------------------------------- Constructors ----------------------------------- //

    private CapabilityTechnicalException(TechnicalError error) {
        super(error);
    }

//    private CapabilityTechnicalException(TechnicalError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    private CapabilityTechnicalException(TechnicalError error, String message) {
//        super(error, message);
//    }
//
//    private CapabilityTechnicalException(TechnicalError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }

    // ----------------------------------- Factories ----------------------------------- //

    public static CapabilityTechnicalException nullDefinition() {
        return new CapabilityTechnicalException(CapabilityTechnicalError.DEFINITION_NULL);
    }

    public static CapabilityTechnicalException nullProvider() {
        return new CapabilityTechnicalException(CapabilityTechnicalError.PROVIDER_NULL);
    }

    public static CapabilityTechnicalException duplicateCode() {
        return new CapabilityTechnicalException(CapabilityTechnicalError.CODE_DUPLICATE);
    }


}