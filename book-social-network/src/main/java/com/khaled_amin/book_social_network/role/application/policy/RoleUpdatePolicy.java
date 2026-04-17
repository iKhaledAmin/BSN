package com.khaled_amin.book_social_network.role.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.role.application.exception.RolePolicyException;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.core.actor.Actor;
import org.springframework.stereotype.Component;

@Component
public class RoleUpdatePolicy extends AbstractPolicy<RolePolicyContext> {

    @Override
    public void validateContext(RolePolicyContext context) {

        if (context.getRole() == null)
            throw RolePolicyException.invalidPolicyContext("Role must not be null");

    }

    @Override
    protected Actor extractActor(RolePolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw RolePolicyException
                .updateForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Update role";
    }

    @Override
    protected void handleSystem(RolePolicyContext context) {
        allow();
    }

    @Override
    protected void handleUser(RolePolicyContext context) {

        Actor actor = context.getActor();
        Role role = context.getRole();

        boolean isSuperAdmin = actor.hasRole(SystemRole.SUPER_ADMIN.getName().value());
        boolean isAdmin = actor.hasRole(SystemRole.ADMIN.getName().value());

        if (!isSuperAdmin || !isAdmin){
            deny("Only SUPER_ADMIN or ADMIN can update roles");
        }

        if (role.isSystemRole()) {

            if (!isSuperAdmin) {
                deny("Only SUPER_ADMIN can update system roles");
            }
        }
        else { // business role

            if (role.isProtectedRole() && !isSuperAdmin) {
                deny("Only SUPER_ADMIN can modify protected roles");
            }

            if (role.isDefaultRole() && !isSuperAdmin){
                deny("Only SUPER_ADMIN can modify default roles");
            }
        }

        allow();
    }
}