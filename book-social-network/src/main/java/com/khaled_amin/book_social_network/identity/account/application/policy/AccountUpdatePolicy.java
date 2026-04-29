package com.khaled_amin.book_social_network.user.application.policy;

import com.khaled_amin.book_social_network.core.policy.AbstractPolicy;
import com.khaled_amin.book_social_network.role.domain.model.SystemRole;
import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.core.actor.ActorIdentity;
import com.khaled_amin.book_social_network.core.actor.ActorType;
import com.khaled_amin.book_social_network.user.application.exception.AccountPolicyException;
import com.khaled_amin.book_social_network.user.domain.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountUpdatePolicy extends AbstractPolicy<AccountPolicyContext> {

    @Override
    public void validateContext(AccountPolicyContext context) {
        if (context.getTarget() == null) {
            throw AccountPolicyException
                    .invalidPolicyContext("Target account cannot be null");
        }
    }

    @Override
    protected Actor extractActor(AccountPolicyContext context) {
        return context.getActor();
    }

    @Override
    protected void deny(String reason) {
        throw AccountPolicyException
                .updateForbidden()
                .withDetail("reason", reason);
    }

    @Override
    protected String getOperationName() {
        return "Update account";
    }


    @Override
    protected void handleSystem(AccountPolicyContext context) {
        allow(); // system can do anything
    }

    @Override
    protected void handleUser(AccountPolicyContext context) {

        Actor actor = context.getActor();
        Account target = context.getTarget();

        // user can update himself
        if  (actor.sameAs(targetIdentity(target))) {
            allow();
        }

        // ADMIN or SUPER_ADMIN can update others
        if (actor.hasAnyRole(SystemRole.ADMIN.getName().value(), SystemRole.SUPER_ADMIN.getName().value())) {
            allow();
        }

        deny("You are not allowed to update this account");
    }


    private ActorIdentity targetIdentity(Account target) {
        return ActorIdentity.of(
                ActorType.USER_ACCOUNT,
                target.getId().toString()
        );
    }
}