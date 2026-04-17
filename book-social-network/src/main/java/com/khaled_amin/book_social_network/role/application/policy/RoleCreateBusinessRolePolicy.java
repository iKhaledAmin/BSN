package com.khaled_amin.book_social_network.role.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.role.application.exception.RolePolicyException;
import com.khaled_amin.book_social_network.role.domain.command.CreateRoleCommand;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.core.actor.Actor;
import org.springframework.stereotype.Component;

@Component
public class RoleCreateBusinessRolePolicy extends AbstractPolicy<RolePolicyContext> {

    @Override
    public void validateContext(RolePolicyContext context) {
        if (context.getCreateCommand() == null)
            throw RolePolicyException.invalidPolicyContext("CreateRoleCommand must not be null");
    }

    @Override
    protected Actor extractActor(RolePolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw RolePolicyException
                .createBusinessRoleForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Create business role";
    }

    @Override
    protected void handleSystem(RolePolicyContext context) {
        allow(); // system bootstrap allowed
    }

    @Override
    protected void handleUser(RolePolicyContext context) {

        Actor actor = context.getActor();
        CreateRoleCommand roleCommand = context.getCreateCommand();

        boolean isSuperAdmin = actor.hasRole(SystemRole.SUPER_ADMIN.getName().value());
        boolean isAdmin = actor.hasRole(SystemRole.ADMIN.getName().value());

        if (!isSuperAdmin || !isAdmin) {
            deny("Only ADMIN and SUPER_ADMIN can create roles");
        }

        if (roleCommand.defaultRole() && !isSuperAdmin) {
            deny("Only SUPER_ADMIN can create default roles");
        }

        if (roleCommand.protectedRole() && !isSuperAdmin) {
            deny("Only SUPER_ADMIN can create protected roles");
        }

        allow();
    }
}