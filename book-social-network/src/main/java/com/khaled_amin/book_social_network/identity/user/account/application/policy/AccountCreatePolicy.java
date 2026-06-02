package com.khaled_amin.book_social_network.identity.user.account.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.user.account.application.actor.AccountActor;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.Role;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class AccountCreatePolicy extends AbstractPolicy<AccountPolicyContext> {

    @Override
    public void validateContext(AccountPolicyContext context) {
        if (context == null)
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason", "Account policy context is null");

        if(context.getActor() == null)
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Actor is null");


        if (context.getRequestedRoles() == null) {
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Requested roles must not be null");
        }
    }


    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException.createForbidden()
                .withClientDetails("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Create account";
    }


    @Override
    protected void handleAnonymous(AccountPolicyContext context) {
        allow(); // Anonymous allowed to resolve accounts (self registration)
    }

    @Override
    protected void handleSystem(AccountPolicyContext context) {
        allow();  // System allowed to resolve accounts (system start up initialization)
    }

    @Override
    protected void handleAccount(AccountPolicyContext context) {

        AccountActor actor =(AccountActor) context.getActor();
        List<Role> roles = context.getRequestedRoles();

        String superAdmin = SystemRole.SUPER_ADMIN.getName().toString();
        String admin = SystemRole.ADMIN.getName().toString();

        if (actor.hasRole(admin) && containsRole(roles,superAdmin)) {
            deny("Admin cannot create super admin account");
        }

        allow();
    }


    private boolean containsRole(List<Role> roles, String roleName) {
        return roles.stream().anyMatch(r -> roleName.equals(r.getName()));
    }
}

