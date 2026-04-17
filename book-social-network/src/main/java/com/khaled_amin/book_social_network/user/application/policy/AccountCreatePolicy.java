package com.khaled_amin.book_social_network.user.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.role.domain.model.Role;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.user.application.exception.AccountPolicyException;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AccountCreatePolicy extends AbstractPolicy<AccountPolicyContext> {

    @Override
    public void validateContext(AccountPolicyContext context) {
        if (context.getRequestedRoles() == null) {
            throw AccountPolicyException.invalidPolicyContext("Requested roles cannot be null");
        }
    }


    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException
                .createForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Create account";
    }


    @Override
    protected void handleAnonymous(AccountPolicyContext context) {
        allow(); // Anonymous allowed to create accounts (self registration)
    }

    @Override
    protected void handleSystem(AccountPolicyContext context) {
        allow();  // System allowed to create accounts (system start up initialization)
    }

    @Override
    protected void handleUser(AccountPolicyContext context) {

        Actor actor = context.getActor();
        List<Role> roles = context.getRequestedRoles();

        if (!actor.hasAnyRole(SystemRole.ADMIN.getName().value(), SystemRole.SUPER_ADMIN.getName().value())) {
            deny("Users cannot create accounts");
        }

        if (actor.hasRole(SystemRole.ADMIN.getName().value())
                && containsRole(roles, SystemRole.SUPER_ADMIN.getName().value())) {
            deny("ADMIN cannot create SUPER_ADMIN");
        }

        allow();
    }


    private boolean containsRole(List<Role> roles, String roleName) {
        return roles.stream()
                .anyMatch(r -> roleName.equals(r.getName()));
    }
}

