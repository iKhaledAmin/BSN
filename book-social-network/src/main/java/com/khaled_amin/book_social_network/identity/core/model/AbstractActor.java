package com.khaled_amin.book_social_network.identity.core.model;

import com.khaled_amin.book_social_network.identity.user.account.application.actor.AccountActor;
import lombok.AllArgsConstructor;


/**
 * Base implementation of the {@link Actor} interface.
 *
 * <p>
 * Provides common functionality shared across all actor implementations,
 * including identity handling and role evaluation helpers.
 * </p>
 *
 * <p>
 * This class is intended to be extended by concrete actor types
 * (e.g., {@link AccountActor}, {@link SystemActor}, {@link AnonymousActor},...).
 * </p>
 *
 * <h3>Responsibilities</h3>
 * <ul>
 *   <li>Encapsulate {@link ActorIdentity} management</li>
 *   <li>Provide default implementations for common operations</li>
 *   <li>Reduce duplication across concrete actor implementations</li>
 * </ul>
 *
 * <h3>Extension Guidelines</h3>
 * <ul>
 *   <li>Subclasses must implement {@link #hasAuthority(String)}</li>
 *   <li>Subclasses should define their own role or scope semantics</li>
 *   <li>ActorSource must be provided via constructor</li>
 * </ul>
 *
 * @see Actor
 * @see ActorIdentity
 * @see ActorType
 */

@AllArgsConstructor
public abstract class AbstractActor implements Actor {

    private final ActorIdentity identity;

    /**
     * {@inheritDoc}
     */
    @Override
    public ActorIdentity getActorIdentity() {
        return identity;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActorType getType() {
        return identity.getActorType();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean sameAs(ActorIdentity otherIdentity) {
        return identity.sameAs(otherIdentity);
    }

    /**
     * {@inheritDoc}
     *
     * <p>
     * Iterates over the provided authorities and delegates to {@link #hasAuthority(String)}.
     * </p>
     */
    @Override
    public boolean hasAnyAuthority(String... authorities) {
        for (String authority : authorities) {
            if (hasAuthority(authority)) return true;
        }
        return false;
    }

}
