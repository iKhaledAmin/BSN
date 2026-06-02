package com.khaled_amin.book_social_network.identity.user.role.exception;

import com.khaled_amin.book_social_network.core.exception.technical.TechnicalError;
import com.khaled_amin.book_social_network.core.exception.technical.TechnicalException;

public class RoleTechnicalException extends TechnicalException {

    // ----------------------------------- Constructors ----------------------------------- //
    protected RoleTechnicalException(TechnicalError error) {
        super(error);
    }

//    protected RoleTechnicalException(TechnicalError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected RoleTechnicalException(TechnicalError error, String message) {
//        super(error, message);
//    }
//
//    protected RoleTechnicalException(TechnicalError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }

    // ----------------------------------- Factories ----------------------------------- //

    public static RoleTechnicalException nullRole() {
        return new RoleTechnicalException(RoleTechnicalError.ROLE_NULL);
    }

    public static RoleTechnicalException nullSystemRole() {
        return new RoleTechnicalException(RoleTechnicalError.SYSTEM_ROLE_NULL);
    }

    public static RoleTechnicalException nullCreateCommand() {
        return new RoleTechnicalException(RoleTechnicalError.CREATE_COMMAND_NULL);
    }

    public static RoleTechnicalException nullUpdateCommand() {
        return new RoleTechnicalException(RoleTechnicalError.UPDATE_COMMAND_NULL);
    }

    public static RoleTechnicalException nullCapability() {
        return new RoleTechnicalException(RoleTechnicalError.CAPABILITY_NULL);
    }

    public static RoleTechnicalException invalidSystemRoleConfiguration() {
        return new RoleTechnicalException(RoleTechnicalError.SYSTEM_ROLE_CONFIGURATION_INVALID);
    }


}
