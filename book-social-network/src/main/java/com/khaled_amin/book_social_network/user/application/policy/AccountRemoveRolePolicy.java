package com.khaled_amin.book_social_network.user.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.core.actor.ActorIdentity;
import com.khaled_amin.book_social_network.core.actor.ActorType;
import com.khaled_amin.book_social_network.user.application.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import org.springframework.stereotype.Component;


@Component
public class AccountRemoveRolePolicy extends AbstractPolicy<AccountPolicyContext> {

    @Override
    public void validateContext(AccountPolicyContext context) {

        if (context.getTarget() == null) {
            throw AccountPolicyException
                    .invalidPolicyContext("Target must not be null");
        }

        if (context.getRequestedRole() == null) {
            throw AccountPolicyException
                    .invalidPolicyContext("Role must not be null");
        }
    }

    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException
                .roleRemovalForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Remove role";
    }


    @Override
    protected void handleSystem(AccountPolicyContext context) {
        // SYSTEM is trusted (batch jobs, bootstrap, internal processes)
        allow();
    }

    @Override
    protected void handleUser(AccountPolicyContext context) {

        Actor actor = context.getActor();
        Account target = context.getTarget();
        Role role = context.getRequestedRole();

        // Prevent self-role removal (except SUPER_ADMIN)
        // A user should not downgrade/remove their own privileges unless they are SUPER_ADMIN
        if (actor.sameAs(targetIdentity(target))
                && !actor.hasRole(SystemRole.SUPER_ADMIN.getName().value())) {

            deny("You cannot remove your own role");
        }

        // Only SUPER_ADMIN can remove SUPER_ADMIN role from any account
        if (SystemRole.SUPER_ADMIN.getName().equals(role.getName())
                && !actor.hasRole(SystemRole.SUPER_ADMIN.getName().value())) {

            deny("Only SUPER_ADMIN can remove SUPER_ADMIN role");
        }

        // System roles are critical → only SUPER_ADMIN is allowed
        if (role.isSystemRole()
                && !actor.hasRole(SystemRole.SUPER_ADMIN.getName().value())) {

            deny("Only SUPER_ADMIN can remove system roles");
        }

        // Business roles can be managed by ADMIN or SUPER_ADMIN
        if (role.isBusinessRole()
                && !actor.hasAnyRole(
                SystemRole.ADMIN.getName().value(),
                SystemRole.SUPER_ADMIN.getName().value())) {

            deny("Only ADMIN or SUPER_ADMIN can remove business roles");
        }

        allow();
    }

    // -------------------- Helpers -------------------- //

    private ActorIdentity targetIdentity(Account target) {
        return ActorIdentity.of(
                ActorType.USER_ACCOUNT,
                target.getId().toString()
        );
    }
}