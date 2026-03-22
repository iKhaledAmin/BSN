package com.khaled_amin.book_social_network.authorization.policy.role;

import com.khaled_amin.book_social_network.authorization.policy.base.Policy;
import com.khaled_amin.book_social_network.authorization.policy.base.PolicyException;
import com.khaled_amin.book_social_network.role.model.enums.SystemRoles;
import org.springframework.stereotype.Component;

@Component
public class RoleRemovalPolicy implements Policy<RolePolicyContext> {

    @Override
    public void check(RolePolicyContext context) {

        var actor = context.getActor();
        var target = context.getTarget();
        var role = context.getRole();

        // Protected system role → only SUPER_ADMIN
        if (role.isProtected() && !actor.hasRole(SystemRoles.SUPER_ADMIN)) {
            throw PolicyException.systemRoleRemovalForbidden();
        }

        // Protected business role → only admin and SUPER_ADMIN
        if (role.isProtected() ){
            if(!(actor.hasRole(SystemRoles.SUPER_ADMIN ) || actor.hasRole(SystemRoles.SUPER_ADMIN ) )) {
                throw PolicyException.businessRoleRemovalForbidden();
            }
        }

        // Cannot remove last role for the account
        if (target.getRoles().size() == 1) {
            throw PolicyException.lastRoleViolation();
        }

        // Prevent self ADMIN removal
        if (actor.getId().equals(target.getId()) &&
                SystemRoles.ADMIN.getSystemCode().equals(role.getSystemCode())) {

            throw PolicyException.selfRoleRemovalForbidden();
        }

        // Prevent removing last SUPER_ADMIN
        if (SystemRoles.SUPER_ADMIN.getSystemCode().equals(role.getSystemCode()) &&
                context.getSuperAdminCount() <= 1) {

            throw PolicyException.lastSuperAdminViolation();
        }
    }
}