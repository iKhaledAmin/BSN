package com.khaled_amin.book_social_network.role.application.exception;

import com.khaled_amin.book_social_network.core.exception.BaseException;

public class RoleApplicationException extends BaseException {

    private RoleApplicationException(RoleApplicationError error, String message) {
        super(error, message);
    }


    // -------------------- Generic -------------------- //

    public static RoleApplicationException of(RoleApplicationError error){
        return new RoleApplicationException(error, error.getMessage());
    }

    public static RoleApplicationException of(RoleApplicationError error , String customMessage){
        return new RoleApplicationException(error, customMessage);
    }


    // -------------------- Specific -------------------- //

    public static RoleApplicationException invalidCommand() {
        return of(RoleApplicationError.INVALID_COMMAND);
    }

    public static RoleApplicationException alreadyExists() {
        return of(RoleApplicationError.ALREADY_EXISTS);
    }

    public static RoleApplicationException deletionViolation() {
        return of(RoleApplicationError.DELETION_VIOLATION);
    }

    public static RoleApplicationException notFound(){
        return of(RoleApplicationError.NOT_FOUND);
    }

    public static RoleApplicationException defaultRoleNotConfigured(){
        return of(RoleApplicationError.DEFAULT_ROLE_NOT_CONFIGURED);
    }

    public static RoleApplicationException rolesNotFound() {
        return of(RoleApplicationError.ROLES_NOT_FOUND);
    }

    public static RoleApplicationException invalidSystemRole() {
        return of(RoleApplicationError.INVALID_SYSTEM_ROLE);
    }
}