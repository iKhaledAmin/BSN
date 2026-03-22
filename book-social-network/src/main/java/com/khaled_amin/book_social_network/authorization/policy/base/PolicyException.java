package com.khaled_amin.book_social_network.authorization.policy.base;

import com.khaled_amin.book_social_network.common.exception.BaseApiException;

public class PolicyException extends BaseApiException {

    private PolicyException(PolicyErrorCode errorCode, String message) {
        super(errorCode, message);
    }

    public static PolicyException of(PolicyErrorCode code) {
        return new PolicyException(code, code.getMessage());
    }

    // Assignment
    public static PolicyException systemRoleAssignmentForbidden() {
        return of(PolicyErrorCode.SYSTEM_ROLE_ASSIGNMENT_FORBIDDEN);
    }

    public static PolicyException businessRoleAssignmentForbidden() {
        return of(PolicyErrorCode.BUSINESS_ROLE_ASSIGNMENT_FORBIDDEN);
    }

    public static PolicyException selfRoleAssignmentForbidden() {
        return of(PolicyErrorCode.SELF_ROLE_ASSIGNMENT_FORBIDDEN);
    }

    // Removal
    public static PolicyException systemRoleRemovalForbidden() {
        return of(PolicyErrorCode.SYSTEM_ROLE_REMOVAL_FORBIDDEN);
    }

    public static PolicyException businessRoleRemovalForbidden() {
        return of(PolicyErrorCode.BUSINESS_ROLE_REMOVAL_FORBIDDEN);
    }

    public static PolicyException selfRoleRemovalForbidden() {
        return of(PolicyErrorCode.SELF_ROLE_REMOVAL_FORBIDDEN);
    }

    // Constraints
    public static PolicyException lastRoleViolation() {
        return of(PolicyErrorCode.LAST_ROLE_VIOLATION);
    }

//    public static PolicyException lastAdminViolation() {
//        return of(PolicyErrorCode.LAST_ADMIN_VIOLATION);
//    }

    public static PolicyException lastSuperAdminViolation(){
        return of(PolicyErrorCode.LAST_SUPER_ADMIN_VIOLATION);
    }
}

//    // -------------------- Advanced (Optional) -------------------- //
//
//    public static PolicyException of(PolicyErrorCode code) {
//        return new PolicyException(code, code.getMessage());
//    }
//
//    public static PolicyException of(PolicyErrorCode code, String customMessage) {
//        return new PolicyException(code, customMessage);
//    }
