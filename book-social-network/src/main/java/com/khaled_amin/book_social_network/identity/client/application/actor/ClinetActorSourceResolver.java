package com.khaled_amin.book_social_network.identity.client.application.actor;

import com.khaled_amin.book_social_network.identity.core.exception.ActorResolutionException;
import com.khaled_amin.book_social_network.identity.core.model.Actor;
import com.khaled_amin.book_social_network.identity.core.model.ActorSource;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;
import com.khaled_amin.book_social_network.identity.core.resolver.ActorSourceResolver;
import com.khaled_amin.book_social_network.identity.client.domain.model.Client;
import com.khaled_amin.book_social_network.identity.user.account.domain.model.Account;

public class ClinetActorSourceResolver implements ActorSourceResolver {
    @Override
    public ActorType getType() {
        return ActorType.CLIENT;
    }

    @Override
    public Actor resolve(ActorSource source) {
        if (!(source instanceof Client client)) {
            throw ActorResolutionException.sourceTypeMismatch(
                    Client.class,
                    source.getClass()
            );
        }

        return new ClientActor(
                client.getId(),
                client.getScopes()
        );
    }
}
