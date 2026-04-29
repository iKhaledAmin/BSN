package com.khaled_amin.book_social_network.user.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.core.actor.ActorIdentity;
import com.khaled_amin.book_social_network.core.actor.ActorType;
import com.khaled_amin.book_social_network.user.application.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountAssignRolePolicy extends AbstractPolicy<AccountPolicyContext> {

    @Override
    public void validateContext(AccountPolicyContext context) {

        if (context.getTarget() == null) {
            throw AccountPolicyException.invalidPolicyContext("Target account must not be null");
        }

        if (context.getRequestedRole() == null) {
            throw AccountPolicyException.invalidPolicyContext("Requested role must not be null");
        }
    }

    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException
                .assignRoleForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Assign role";
    }


    @Override
    protected void handleSystem(AccountPolicyContext context) {
        allow(); // system trusted
    }


    @Override
    protected void handleUser(AccountPolicyContext context) {

        Actor actor = context.getActor();
        var target = context.getTarget();
        var role = context.getRequestedRole();



        // --------------------  Prevent privilege escalation --------------------
        // Only SUPER_ADMIN can assign SUPER_ADMIN role
        if (SystemRole.SUPER_ADMIN.getName().value().equals(role.getName())
                && !actor.hasRole(SystemRole.SUPER_ADMIN.getName().value())) {

            deny("Only SUPER_ADMIN can assign SUPER_ADMIN role");
        }

        // -------------------- System roles protection --------------------
        // Only SUPER_ADMIN can assign any system role
        if (role.isSystemRole()
                && !actor.hasRole(SystemRole.SUPER_ADMIN.getName().value())) {

            deny("Only SUPER_ADMIN can assign system roles");
        }

        // -------------------- Business roles control --------------------
        // Only ADMIN or SUPER_ADMIN can assign business roles
        if (role.isBusinessRole()
                && !actor.hasAnyRole(
                SystemRole.ADMIN.getName().value(),
                SystemRole.SUPER_ADMIN.getName().value())) {

            deny("Only ADMIN or SUPER_ADMIN can assign business roles");
        }

        // -------------------- Self-assignment restriction --------------------
        // Prevent users from elevating themselves
        if (actor.sameAs(targetIdentity(target))
                && !actor.hasRole(SystemRole.SUPER_ADMIN.getName().value())) {

            deny("You cannot assign roles to yourself");
        }

        allow();
    }


    private ActorIdentity targetIdentity(Account target) {
        return ActorIdentity.of(
                ActorType.USER_ACCOUNT,
                target.getId().toString()
        );
    }
}