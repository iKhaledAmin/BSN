package com.khaled_amin.book_social_network.identity.user.role.exception;

import com.khaled_amin.book_social_network.core.exception.business.BusinessError;
import com.khaled_amin.book_social_network.core.exception.business.BusinessException;

public class RoleBusinessException extends BusinessException {

    // ----------------------------------- Constructors ----------------------------------- //
    protected RoleBusinessException(BusinessError error) {
        super(error);
    }

//    protected RoleBusinessException(BusinessError error, Throwable cause) {
//        super(error, cause);
//    }
//
//    protected RoleBusinessException(BusinessError error, String message) {
//        super(error, message);
//    }
//
//    protected RoleBusinessException(BusinessError error, String message, Throwable cause) {
//        super(error, message, cause);
//    }

    // ----------------------------------- Factories ----------------------------------- //

    // -------------------------------- Retrieval -------------------------------- //

    public static RoleBusinessException notFound() {
        return new RoleBusinessException(RoleBusinessError.NOT_FOUND);
    }

    public static RoleBusinessException someRolesNotFound() {
        return new RoleBusinessException(RoleBusinessError.SOME_NOT_FOUND);
    }

    // -------------------------------- Invariants -------------------------------- //

    public static RoleBusinessException systemRoleMustBeProtected() {
        return new RoleBusinessException(
                RoleBusinessError.SYSTEM_ROLE_MUST_BE_PROTECTED
        );
    }

    public static RoleBusinessException defaultRoleMustBeProtected() {
        return new RoleBusinessException(
                RoleBusinessError.DEFAULT_ROLE_MUST_BE_PROTECTED
        );
    }

    // -------------------------------- Update Restrictions -------------------------------- //

    public static RoleBusinessException systemRoleCannotBeModified() {
        return new RoleBusinessException(
                RoleBusinessError.SYSTEM_ROLE_CANNOT_BE_MODIFIED
        );
    }

    // -------------------------------- Delete Restrictions -------------------------------- //

    public static RoleBusinessException protectedRoleCannotBeDeleted() {
        return new RoleBusinessException(
                RoleBusinessError.PROTECTED_ROLE_CANNOT_BE_DELETED
        );
    }

    public static RoleBusinessException roleAssignedToAccounts() {
        return new RoleBusinessException(
                RoleBusinessError.ROLE_ASSIGNED_TO_ACCOUNTS
        );
    }

    // -------------------------------- Uniqueness -------------------------------- //

    public static RoleBusinessException nameAlreadyExists() {
        return new RoleBusinessException(
                RoleBusinessError.NAME_ALREADY_EXISTS
        );
    }

    public static RoleBusinessException displayNameAlreadyExists() {
        return new RoleBusinessException(
                RoleBusinessError.DISPLAY_NAME_ALREADY_EXISTS
        );
    }

    // -------------------------------- Capability Assignment -------------------------------- //

    public static RoleBusinessException capabilityAlreadyAssigned() {
        return new RoleBusinessException(
                RoleBusinessError.CAPABILITY_ALREADY_ASSIGNED
        );
    }

    public static RoleBusinessException capabilityNotAssigned() {
        return new RoleBusinessException(
                RoleBusinessError.CAPABILITY_NOT_ASSIGNED
        );
    }

    public static RoleBusinessException systemManagedCapabilityCannotBeAssigned() {
        return new RoleBusinessException(
                RoleBusinessError.SYSTEM_MANAGED_CAPABILITY_CANNOT_BE_ASSIGNED
        );
    }

    public static RoleBusinessException systemManagedCapabilityCannotBeRemoved() {
        return new RoleBusinessException(
                RoleBusinessError.SYSTEM_MANAGED_CAPABILITY_CANNOT_BE_REMOVED
        );
    }
}
