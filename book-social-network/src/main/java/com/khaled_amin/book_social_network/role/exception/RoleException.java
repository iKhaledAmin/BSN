package com.khaled_amin.book_social_network.role.exception;

import com.khaled_amin.book_social_network.common.exception.BaseApiException;
import com.khaled_amin.book_social_network.role.error.RoleErrorCode;

public class RoleException extends BaseApiException {

    private RoleException(RoleErrorCode code, String message) {
        super(code, message);
    }

    public static RoleException notFound() {
        return new RoleException(
                RoleErrorCode.ROLE_NOT_FOUND,
                RoleErrorCode.ROLE_NOT_FOUND.getMessage()
        );
    }

    public static RoleException alreadyExists() {
        return new RoleException(
                RoleErrorCode.ROLE_ALREADY_EXISTS,
                RoleErrorCode.ROLE_ALREADY_EXISTS.getMessage()
        );
    }

    public static RoleException defaultRoleNotConfigured() {
        return new RoleException(
                RoleErrorCode.DEFAULT_ROLE_NOT_CONFIGURED,
                RoleErrorCode.DEFAULT_ROLE_NOT_CONFIGURED.getMessage()
        );
    }

    public static RoleException protectedRole() {
        return new RoleException(
                RoleErrorCode.ROLE_PROTECTED,
                RoleErrorCode.ROLE_PROTECTED.getMessage()
        );
    }
}