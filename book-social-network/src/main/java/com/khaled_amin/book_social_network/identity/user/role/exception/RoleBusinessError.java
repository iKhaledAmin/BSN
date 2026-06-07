package com.khaled_amin.book_social_network.identity.user.role.exception;

import com.khaled_amin.book_social_network.core.constant.SystemDomain;
import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum RoleBusinessError implements BusinessError {

    // -------------------------------- Retrieval -------------------------------- //

    NOT_FOUND(
            SystemDomain.ROLE,
            "ROLE_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Role not found"
    ),

    SOME_NOT_FOUND(
            SystemDomain.ROLE,
            "ROLE_SOME_ROLES_NOT_FOUND",
            HttpStatus.NOT_FOUND,
            "Some requested roles were not found"
    ),

    // -------------------------------- Invariants -------------------------------- //

    SYSTEM_ROLE_MUST_BE_PROTECTED(
            SystemDomain.ROLE,
            "ROLE_SYSTEM_ROLE_MUST_BE_PROTECTED",
            HttpStatus.CONFLICT,
            "System role must always be protected"
    ),

    DEFAULT_ROLE_MUST_BE_PROTECTED(
            SystemDomain.ROLE,
            "ROLE_DEFAULT_ROLE_MUST_BE_PROTECTED",
            HttpStatus.CONFLICT,
            "Default role must always be protected"
    ),

    // -------------------------------- Update Restrictions -------------------------------- //

    SYSTEM_ROLE_CANNOT_BE_MODIFIED(
            SystemDomain.ROLE,
            "ROLE_SYSTEM_ROLE_CANNOT_BE_MODIFIED",
            HttpStatus.CONFLICT,
            "System role cannot be modified"
    ),

    // -------------------------------- Delete Restrictions -------------------------------- //

    PROTECTED_ROLE_CANNOT_BE_DELETED(
            SystemDomain.ROLE,
            "ROLE_PROTECTED_ROLE_CANNOT_BE_DELETED",
            HttpStatus.CONFLICT,
            "Protected role cannot be deleted"
    ),

    ROLE_ASSIGNED_TO_ACCOUNTS(
            SystemDomain.ROLE,
            "ROLE_ASSIGNED_TO_ACCOUNTS",
            HttpStatus.CONFLICT,
            "Role is assigned to one or more accounts"
    ),

    // -------------------------------- Uniqueness -------------------------------- //

    NAME_ALREADY_EXISTS(
            SystemDomain.ROLE,
            "ROLE_NAME_ALREADY_EXISTS",
            HttpStatus.CONFLICT,
            "Role name already exists"
    ),

    DISPLAY_NAME_ALREADY_EXISTS(
            SystemDomain.ROLE,
            "ROLE_DISPLAY_NAME_ALREADY_EXISTS",
            HttpStatus.CONFLICT,
            "Role display name already exists"
    ),

    // -------------------------------- Capability Assignment -------------------------------- //

    CAPABILITY_ALREADY_ASSIGNED(
            SystemDomain.ROLE,
            "ROLE_CAPABILITY_ALREADY_ASSIGNED",
            HttpStatus.CONFLICT,
            "Capability already assigned to role"
    ),

    CAPABILITY_NOT_ASSIGNED(
            SystemDomain.ROLE,
            "ROLE_CAPABILITY_NOT_ASSIGNED",
            HttpStatus.CONFLICT,
            "Capability is not assigned to role"
    ),

    SYSTEM_MANAGED_CAPABILITY_CANNOT_BE_ASSIGNED(
            SystemDomain.ROLE,
            "ROLE_SYSTEM_MANAGED_CAPABILITY_CANNOT_BE_ASSIGNED",
            HttpStatus.CONFLICT,
            "System managed capability cannot be assigned"
    ),

    SYSTEM_MANAGED_CAPABILITY_CANNOT_BE_REMOVED(
            SystemDomain.ROLE,
            "ROLE_SYSTEM_MANAGED_CAPABILITY_CANNOT_BE_REMOVED",
            HttpStatus.CONFLICT,
            "System managed capability cannot be removed"
    );

    private final SystemDomain domain;
    private final String code;
    private final HttpStatus status;
    private final String message;
}