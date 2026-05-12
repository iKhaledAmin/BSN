package com.khaled_amin.book_social_network.identity.user.account.application.actor;

import com.khaled_amin.book_social_network.identity.core.model.AbstractActor;
import com.khaled_amin.book_social_network.identity.core.model.ActorCode;
import com.khaled_amin.book_social_network.identity.core.model.ActorIdentity;
import com.khaled_amin.book_social_network.identity.core.model.ActorType;

import java.util.Set;

/**
 * Business actor representation of an authenticated account.
 *
 * <p>
 * This actor is the lightweight authorization-facing abstraction
 * used throughout the application layer instead of exposing
 * the full {@code Account} aggregate.
 * </p>
 *
 * <p>
 * The actor identity is based on:
 * </p>
 * <ul>
 *     <li>{@link ActorType#ACCOUNT}</li>
 *     <li>Stable {@link ActorCode}</li>
 * </ul>
 *
 * <p>
 * Authorities are resolved from assigned role names.
 * </p>
 */
public class AccountActor extends AbstractActor {

    private final Set<String> roleNames;

    public AccountActor(ActorCode actorCode, Set<String> roleNames) {
        super(
                ActorIdentity.of(ActorType.ACCOUNT, actorCode)
        );

        this.roleNames = roleNames;
    }

    @Override
    public boolean hasAuthority(String authority) {
        return roleNames.contains(authority);
    }
}