package com.khaled_amin.book_social_network.identity.user.account.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.identity.user.account.application.actor.AccountActor;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.identity.user.account.exception.AccountTechnicalException;
import com.khaled_amin.book_social_network.identity.user.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountUpdatePolicy extends AbstractPolicy<AccountPolicyContext> {


    @Override
    public void validateContext(AccountPolicyContext context) {
        if (context == null)
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason", "Account policy context is null");

        if(context.getActor() == null)
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Actor is null");

        if (context.getTarget() == null) {
            throw AccountTechnicalException.invalidPolicyContext()
                    .withDebugDetails("reason","Target account must not be null");
        }

    }

    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException.updateForbidden()
                .withClientDetails("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Update account";
    }


    @Override
    protected void handleSystem(AccountPolicyContext context) {
        allow();
    }

    @Override
    protected void handleAccount(AccountPolicyContext context) {

        AccountActor actor = (AccountActor) context.getActor();
        Account target = context.getTarget();

        String superAdmin = SystemRole.SUPER_ADMIN.getName().toString();

        // only SUPER_ADMIN can update SUPER_ADMIN
        if (!actor.hasRole(superAdmin) && target.hasRole(superAdmin)){
            deny("You are not allowed to update this account");
        }

        // any account can update himself
        // only SUPER_ADMIN can update others
        if  (!actor.sameAs(target.getActorIdentity()) && !actor.hasRole(superAdmin)) {
            deny("You are not allowed to update this account");

        }

        allow();

    }

}