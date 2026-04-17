package com.khaled_amin.book_social_network.user.application.actor;

import com.khaled_amin.book_social_network.core.actor.Actor;
import com.khaled_amin.book_social_network.core.actor.ActorProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class AccountActorResolver {
    private final ActorProvider actorProvider;

    public UserAccountActor getCurrentAccount() {
        Actor actor = actorProvider.getCurrentActor();

        if (actor instanceof UserAccountActor user) {
            return user;
        }

        throw new AuthenticationCredentialsNotFoundException(
                "No authenticated account found"
        );
    }

    public Long getCurrentAccountId() {
        return Long.valueOf(getCurrentAccount().getIdentity().id());
    }
}
