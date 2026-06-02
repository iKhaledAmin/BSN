package com.khaled_amin.book_social_network.identity.user.role.exception;

import com.khaled_amin.book_social_network.core.exception.validation.ValidationError;
import com.khaled_amin.book_social_network.core.exception.validation.ValidationException;

public class RoleValidationException extends ValidationException {

    // ----------------------------------- Constructors ----------------------------------- //

    protected RoleValidationException(ValidationError error) {
        super(error);
    }

//    protected RoleValidationException(ValidationError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected RoleValidationException(ValidationError error, String message) {
//        super(error, message);
//    }
//
//    protected RoleValidationException(ValidationError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }

    // ----------------------------------- Factories ----------------------------------- //

    public static RoleValidationException invalidId() {
        return new RoleValidationException(RoleValidationError.ID_INVALID);
    }

    public static RoleValidationException invalidName() {
        return new RoleValidationException(RoleValidationError.NAME_INVALID);
    }

    public static RoleValidationException invalidDisplayName() {
        return new RoleValidationException(RoleValidationError.DISPLAY_NAME_INVALID);
    }

    public static RoleValidationException invalidDescription() {
        return new RoleValidationException(RoleValidationError.DESCRIPTION_INVALID);
    }

}