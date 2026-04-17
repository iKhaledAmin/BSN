package com.khaled_amin.book_social_network.role.domain.exception;

import com.khaled_amin.book_social_network.core.exception.BaseException;

public class RoleDomainException extends BaseException {

    private RoleDomainException(RoleDomainError error, String message) {
        super(error, message);
    }


    // -------- Generic -------- //

    public static RoleDomainException of(RoleDomainError error) {
        return new RoleDomainException(error, error.getMessage());
    }

    public static RoleDomainException of(RoleDomainError error, String message) {
        return new RoleDomainException(error, message);
    }

    // -------- Specific -------- //

    public static RoleDomainException invalidRoleName() {
        return of(RoleDomainError.INVALID_ROLE_NAME);
    }

    public static RoleDomainException invalidRoleDisplayName() {
        return of(RoleDomainError.INVALID_ROLE_DISPLAY_NAME);
    }

    public static RoleDomainException invalidRoleDescription() {
        return of(RoleDomainError.INVALID_ROLE_DESCRIPTION);
    }

    public static RoleDomainException invalidRoleType() {
        return of(RoleDomainError.INVALID_ROLE_TYPE);
    }

    public static RoleDomainException invalidSystemRole() {
        return of(RoleDomainError.INVALID_SYSTEM_ROLE);
    }

    public static RoleDomainException invalidRoleId() {
        return of(RoleDomainError.INVALID_ROLE_ID);
    }

    public static RoleDomainException invalidCommand() {
        return of(RoleDomainError.INVALID_COMMAND);
    }

    public static RoleDomainException invalidRoleState() {
        return of(RoleDomainError.INVALID_ROLE_STATE);
    }

    public static RoleDomainException protectedRoleViolation() {
        return of(RoleDomainError.PROTECTED_ROLE_VIOLATION);
    }
}