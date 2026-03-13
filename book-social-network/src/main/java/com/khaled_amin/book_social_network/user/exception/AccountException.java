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
                "Role not found"
        );
    }

    public static RoleException alreadyExists() {
        return new RoleException(
                RoleErrorCode.ROLE_ALREADY_EXISTS,
                "Role already exists"
        );
    }
}