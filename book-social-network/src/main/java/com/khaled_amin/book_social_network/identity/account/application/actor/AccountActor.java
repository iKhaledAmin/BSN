package com.khaled_amin.book_social_network.identity.account.application.actor;

import com.khaled_amin.book_social_network.identity.actor.AbstractActor;
import com.khaled_amin.book_social_network.identity.actor.ActorIdentity;
import com.khaled_amin.book_social_network.identity.actor.ActorType;

import java.util.Set;

public class UserAccountActor extends AbstractActor {

    private final Set<String> roleNames;

    public UserAccountActor(Long accountId, Set<String> roleNames) {
        super(
                 ActorIdentity.of(
                        ActorType.USER_ACCOUNT,
                        accountId.toString()
                )
        );

        this.roleNames = roleNames;
    }

    @Override
    public boolean hasRole(String roleName) {
        return roleNames.contains(roleName);
    }

}
