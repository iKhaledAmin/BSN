package com.khaled_amin.book_social_network.identity.core.model;


/**
 * Represents an unauthenticated actor interacting with the system.
 *
 * <p>
 * This actor is used for requests that do not carry any authenticated identity,
 * such as public API access (e.g., registration, login, browsing public data).
 * </p>
 *
 * <h3>Characteristics</h3>
 * <ul>
 *   <li>Has a fixed {@link ActorType#ANONYMOUS} identity</li>
 *   <li>Does not possess any roles</li>
 *   <li>Used as a safe default for unauthenticated contexts</li>
 * </ul>
 *
 * <h3>Security Semantics</h3>
 * <ul>
 *   <li>Always denied in role-based checks</li>
 *   <li>Policies must explicitly allow anonymous access where required</li>
 * </ul>
 *
 * @see ActorType
 * @see AbstractActor
 */

public class AnonymousActor extends AbstractActor {

    public AnonymousActor() {
        super(ActorIdentity.of(ActorType.ANONYMOUS, "ANONYMOUS"));
    }

    @Override
    public boolean hasAuthority(String authority) {
        return false;
    }

}