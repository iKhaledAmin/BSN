package com.khaled_amin.book_social_network.identity.user.role.application.policy;

import com.khaled_amin.book_social_network.core.policy.core.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.user.role.application.exception.RolePolicyException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import org.springframework.stereotype.Component;

@Component
public class RoleDeletePolicy extends AbstractPolicy<RolePolicyContext> {

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
                .deleteForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Delete role";
    }

    @Override
    protected void handleSystem(RolePolicyContext context) {
        allow();
    }

    @Override
    protected void handleAccount(RolePolicyContext context) {

        Actor actor = context.getActor();
        Role role = context.getRole();

        boolean isSuperAdmin = actor.hasAuthority(SystemRole.SUPER_ADMIN.getName().value());
        boolean isAdmin = actor.hasAuthority(SystemRole.ADMIN.getName().value());

        if (role.isSystemRole()) {
            deny("System roles cannot be deleted");
        }

        if (!isSuperAdmin || !isAdmin){
            deny("Only SUPER_ADMIN or ADMIN can delete roles");
        }

        if (role.isDefaultRole()) {
            deny("Default roles cannot be deleted");
        }

        if (role.isProtectedRole() && !isSuperAdmin) {
            deny("Only SUPER_ADMIN can delete protected roles");
        }

        allow();
    }
}
