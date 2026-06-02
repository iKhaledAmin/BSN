package com.khaled_amin.book_social_network.identity.user.account.application.actor;

import com.khaled_amin.book_social_network.identity.core.exception.IdentityTechnicalException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorSourceResolver;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;

public class AccountActorSourceResolver implements ActorSourceResolver {

    @Override
    public ActorType getType() {
        return ActorType.ACCOUNT;
    }

    @Override
    public Actor resolve(ActorSource source) {

        if (!(source instanceof Account account)) {
            throw IdentityTechnicalException.sourceTypeMismatch(
                    Account.class,
                    source.getClass()
            );
        }
        return new AccountActor(
                account.getAccountCode(),
                account.getRoleNames(),
                account.getRoleNames()
        );
    }
}
