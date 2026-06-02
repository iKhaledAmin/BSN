package com.khaled_amin.book_social_network.identity.capability.exception;

import com.khaled_amin.book_social_network.core.exception.validation.ValidationError;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationException;

public class CapabilityValidationException extends ValidationException {

    // ----------------------------------- Constructors ----------------------------------- //
    protected CapabilityValidationException(ValidationError error) {
        super(error);
    }

//    protected CapabilityValidationException(ValidationError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected CapabilityValidationException(ValidationError error, String message) {
//        super(error, message);
//    }
//
//    protected CapabilityValidationException(ValidationError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }



    // ----------------------------------- Factories ----------------------------------- //


    public static CapabilityValidationException invalidCode() {
        return new CapabilityValidationException(CapabilityValidationError.CODE_INVALID);
    }

    public static CapabilityValidationException invalidResource() {
        return new CapabilityValidationException(CapabilityValidationError.RESOURCE_INVALID);
    }

    public static CapabilityValidationException invalidAction() {
        return new CapabilityValidationException(CapabilityValidationError.ACTION_INVALID);
    }

    public static CapabilityValidationException invalidName() {
        return new CapabilityValidationException(CapabilityValidationError.NAME_INVALID);
    }

    public static CapabilityValidationException invalidDescription() {
        return new CapabilityValidationException(CapabilityValidationError.DESCRIPTION_INVALID);
    }

}
