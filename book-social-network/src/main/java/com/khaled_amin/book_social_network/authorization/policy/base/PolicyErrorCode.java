package com.khaled_amin.book_social_network.authorization.policy.base;

import com.khaled_amin.book_social_network.common.error.ApiErrorCode;
import org.springframework.http.HttpStatus;

public enum PolicyErrorCode implements ApiErrorCode {

    // -------------------- Generic -------------------- //
    POLICY_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "Operation is not allowed"
    ),

    // -------------------- Role Assignment -------------------- //
    SYSTEM_ROLE_ASSIGNMENT_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "Only SUPER_ADMIN can assign this role"
    ),

    BUSINESS_ROLE_ASSIGNMENT_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "Only ADMIN or SUPER_ADMIN can assign this role"
    ),

    SELF_ROLE_ASSIGNMENT_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "You cannot assign a role to yourself"
    ),

    // -------------------- Role Removal -------------------- //
    SYSTEM_ROLE_REMOVAL_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "Only SUPER_ADMIN can remove this role"
    ),

    BUSINESS_ROLE_REMOVAL_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "Only ADMIN or SUPER_ADMIN can remove this role"
    ),

    SELF_ROLE_REMOVAL_FORBIDDEN(
            HttpStatus.FORBIDDEN,
            "You cannot remove your own critical role"
    ),

    // -------------------- Business Constraints -------------------- //
    LAST_ROLE_VIOLATION(
            HttpStatus.UNPROCESSABLE_CONTENT,
            "An account must have at least one role"
    ),

    LAST_SUPER_ADMIN_VIOLATION(
            HttpStatus.UNPROCESSABLE_ENTITY,
            "Cannot remove the last SUPER_ADMIN in the system"
    );

    private final HttpStatus status;
    private final String message;

    PolicyErrorCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }

    @Override
    public HttpStatus getStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}