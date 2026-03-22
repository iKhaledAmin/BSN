package com.khaled_amin.book_social_network.authorization.policy.role;

import com.khaled_amin.book_social_network.authorization.policy.base.Policy;
import com.khaled_amin.book_social_network.authorization.policy.base.PolicyException;
import com.khaled_amin.book_social_network.role.model.entity.Role;
import com.khaled_amin.book_social_network.role.model.enums.SystemRoles;
import org.springframework.stereotype.Component;

@Component
public class RoleAssignmentPolicy implements Policy<RolePolicyContext> {

    @Override
    public void check(RolePolicyContext context) {

        Role role = context.getRole();
        var actor = context.getActor();
        var target = context.getTarget();

        boolean isSystemProtected = role.isProtectedRole() && role.isSystemRole();
        boolean isBusinessProtected = role.isProtectedRole() && !role.isSystemRole();


        // Self role assignment → only SUPER_ADMIN allowed
        if (actor.getId().equals(target.getId()) &&
                !actor.hasRole(SystemRoles.SUPER_ADMIN)) {

            throw PolicyException.selfRoleAssignmentForbidden();
        }

        // System-protected roles → only SUPER_ADMIN
        if (isSystemProtected && !actor.hasRole(SystemRoles.SUPER_ADMIN)) {
            throw PolicyException.systemRoleAssignmentForbidden();
        }

        // Business-protected roles → ADMIN or SUPER_ADMIN
        if (isBusinessProtected &&
                !(actor.hasRole(SystemRoles.ADMIN) || actor.hasRole(SystemRoles.SUPER_ADMIN))) {

            throw PolicyException.businessRoleAssignmentForbidden();
        }
    }
}
