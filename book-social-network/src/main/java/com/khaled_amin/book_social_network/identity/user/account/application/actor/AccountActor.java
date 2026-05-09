package com.khaled_amin.book_social_network.identity.user.account.application.actor;

import com.khaled_amin.book_social_network.identity.core.model.AbstractActor;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

import java.util.Set;

public class AccountActor extends AbstractActor {

    private final Set<String> roleNames;

    public AccountActor(Long accountId, Set<String> roleNames) {
        super(
             ActorIdentity.of(ActorType.ACCOUNT, accountId.toString())
        );

        this.roleNames = roleNames;
    }

    @Override
    public boolean hasAuthority(String authority) {
        return roleNames.contains(authority);
    }

}
