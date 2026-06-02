package com.khaled_amin.book_social_network.identity.user.account.application.actor;

import com.khaled_amin.book_social_network.identity.core.exception.IdentityTechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorPrincipalResolver;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.security.principal.account.AccountPrincipal;
import com.khaled_amin.book_social_network.security.principal.core.AuthenticatedPrincipal;
import org.springframework.stereotype.Component;

@Component
public class AccountActorPrincipalResolver implements ActorPrincipalResolver {

    @Override
    public ActorType getType() {
        return ActorType.ACCOUNT;
    }

    @Override
    public Actor resolve(AuthenticatedPrincipal principal) {

        if (!(principal instanceof AccountPrincipal account)) {
            throw IdentityTechnicalException.principalTypeMismatch(
                    AccountPrincipal.class,
                    principal.getClass()
            );
        }

        return new AccountActor(
                account.getActorCode(),
                account.getRoles(),
                account.getPermissions()
        );
    }
}